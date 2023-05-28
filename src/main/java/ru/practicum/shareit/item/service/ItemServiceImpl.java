package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.status.BookingStatus;
import ru.practicum.shareit.booking.utils.BookingMapper;
import ru.practicum.shareit.comment.dto.RequestDto;
import ru.practicum.shareit.comment.dto.ResponseDto;
import ru.practicum.shareit.comment.repository.CommentRepository;
import ru.practicum.shareit.comment.utils.CommentMapper;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.ItemNotValidException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.exception.UserNotValidException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.utils.ItemMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.utils.UserMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;
    private final BookingMapper bookingMapper;
    private final CommentMapper commentMapper;


    @Override
    @Transactional
    public ItemDto createItem(ItemDto itemDto, long userId) {
        if (itemDto.getAvailable() == null) {
            throw new ItemNotValidException("available");
        }
        if (itemDto.getName().isBlank()) {
            throw new ItemNotValidException("name");
        }
        if (itemDto.getDescription() == null) {
            throw new ItemNotValidException("description");
        }
        User user = getUser(userId);
        return itemMapper.toItemDto(itemRepository.save(itemMapper.toItem(itemDto, user)), userMapper.toUserDto(user));
    }

    @Override
    public ItemDto readItem(long itemId, long requesterId) {
        Item item = getItem(itemId);

        UserDto userDto = userMapper.toUserDto(item.getOwner());
        ItemDto itemDto = itemMapper.toItemDto(item, userDto);
        setBookings(itemDto, requesterId);
        setComments(itemDto);

        return itemDto;
    }

    @Override
    @Transactional
    public ItemDto updateItem(long userId, ItemDto itemDto, long itemId) {
        User user = getUser(userId);
        Item oldItem = getItem(itemId);
        validateOwner(userId, oldItem);

        Item updatedItem = Item.builder()
                .id(itemId)
                .owner(oldItem.getOwner())
                .request(oldItem.getRequest())
                .name(Objects.requireNonNullElse(itemDto.getName(), oldItem.getName()))
                .description(Objects.requireNonNullElse(itemDto.getDescription(), oldItem.getDescription()))
                .available(Objects.requireNonNullElse(itemDto.getAvailable(), oldItem.getAvailable()))
                .build();

        itemRepository.save(updatedItem);
        return itemMapper.toItemDto(updatedItem, userMapper.toUserDto(user));
    }

    @Override
    @Transactional
    public void deleteItem(long userId, long itemId) {
        validateOwner(getUser(userId).getId(), getItem(itemId));
        itemRepository.deleteById(itemId);
    }

    @Override
    public List<ItemDto> getAllItems(long userId) {
        UserDto userDto = userMapper.toUserDto(getUser(userId));
        List<Item> items = itemRepository.findByOwnerIdOrderByIdAsc(userId);

        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : items) {
            ItemDto itemDto = itemMapper.toItemDto(item, userDto);
            setBookings(itemDto, userDto.getId());
            setComments(itemDto);
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }

    @Override
    public List<ItemDto> getAvailableItemsByText(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : itemRepository.findByNameLikeIgnoreCaseOrDescriptionLikeIgnoreCaseAndAvailableOrderByIdDesc(
                text, true)) {
            UserDto userDto = userMapper.toUserDto(item.getOwner());
            ItemDto itemDto = itemMapper.toItemDto(item, userDto);

            setBookings(itemDto, item.getOwner().getId());
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }

    @Override
    @Transactional
    public ResponseDto addComment(Long userId, Long itemId, RequestDto requestDto) {
        User user = getUser(userId);
        Item item = getItem(itemId);

        List<Booking> bookings =
                bookingRepository.findByItemIdAndBookerIdAndEndLessThanAndStatus(itemId, userId,
                        LocalDateTime.now(), BookingStatus.APPROVED
                );
        if (bookings.isEmpty()) {
            throw new ItemNotValidException(item.getName());
        }

        return commentMapper.toResponseDto(commentRepository.save(
                commentMapper.toComment(requestDto, item, user, LocalDateTime.now())), userMapper.toUserDto(user));
    }

    private User getUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    private void validateOwner(Long userId, Item item) {
        if (!Objects.equals(item.getOwner().getId(), userId)) {
            throw new UserNotValidException(userId);
        }
    }

    private void setBookings(ItemDto itemDto, Long requestUserId) {
        if (Objects.equals(itemDto.getOwner().getId(), requestUserId)) {
            List<Booking> bookings = bookingRepository.findByItemIdOrderByIdDesc(itemDto.getId());

            Booking lastBooking =
                    bookings.stream().filter(booking -> !booking.getStatus().equals(BookingStatus.REJECTED))
                            .filter(booking -> booking.getEnd().isBefore(LocalDateTime.now().plusHours(1)))
                            .findFirst().orElse(null);

            Booking nextBooking =
                    bookings.stream().filter(booking -> booking.getStatus().equals(BookingStatus.APPROVED))
                            .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                            .findFirst().orElse(null);

            itemDto.setLastBooking(Objects.isNull(lastBooking) ? null :
                    bookingMapper.toItemResponseDto(lastBooking, userMapper.toUserDto(lastBooking.getBooker())));
            itemDto.setNextBooking(Objects.isNull(nextBooking) ? null :
                    bookingMapper.toItemResponseDto(nextBooking, userMapper.toUserDto(nextBooking.getBooker())));
        }
    }

    private void setComments(ItemDto itemDto) {
        itemDto.setComments(commentRepository.findByItemId(itemDto.getId()).stream()
                .map(comment -> commentMapper.toResponseDto(comment, userMapper.toUserDto(comment.getAuthor())))
                .collect(Collectors.toList()));
    }
}
