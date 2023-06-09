package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.status.BookingStatus;
import ru.practicum.shareit.booking.utils.BookingMapper;
import ru.practicum.shareit.comment.dto.CommentRequestDto;
import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.comment.repository.CommentRepository;
import ru.practicum.shareit.comment.utils.CommentMapper;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.exception.EntityNotValidException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.utils.ItemMapper;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.utils.UserMapper;
import ru.practicum.shareit.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final BookingMapper bookingMapper;
    private final CommentMapper commentMapper;
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public ItemDto createItem(ItemRequestDto itemRequestDto, long userId) {
        User user = getUser(userId);
        Item item = itemMapper.toItem(itemRequestDto, user);

        if (Objects.nonNull(itemRequestDto.getRequestId())) {
            Request request = requestRepository.findById(itemRequestDto.getRequestId()).orElseThrow(() -> new EntityNotFoundException("request", itemRequestDto.getRequestId()));
            item.setRequest(request);
        }
        return itemMapper.toItemDto(itemRepository.save(item), userMapper.toUserDto(user));
    }

    @Override
    public ItemDto readItem(long itemId, long requesterId) {
        Item item = getItem(itemId);
        Map<Item, List<Booking>> bookings = getBookings(List.of(item));
        Map<Item, List<Comment>> comments = getComments(List.of(item));

        UserDto userDto = userMapper.toUserDto(item.getOwner());
        ItemDto itemDto = itemMapper.toItemDto(item, userDto);
        setBookings(itemDto, requesterId, bookings.get(item), DateUtils.getCurrentTime());
        setComments(itemDto, comments.get(item));

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
                .name(itemDto.getName() == null || itemDto.getName().isBlank() ? oldItem.getName() : itemDto.getName())
                .description(itemDto.getDescription() == null || itemDto.getDescription().isBlank() ?
                        oldItem.getDescription() : itemDto.getDescription())
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
        Map<Item, List<Comment>> comments = getComments(items);
        Map<Item, List<Booking>> bookings = getBookings(items);

        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : items) {
            ItemDto itemDto = itemMapper.toItemDto(item, userDto);
            setBookings(itemDto, userDto.getId(), bookings.get(item), DateUtils.getCurrentTime());
            setComments(itemDto, comments.get(item));
            itemDto.setComments(comments.getOrDefault(item,
                    List.of()).stream().map(e -> commentMapper.toResponseDto(e, userDto)).collect(toList()));
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }

    @Override
    public List<ItemDto> getAvailableItemsByText(String text, long userId, PageRequest pageRequest) {
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : itemRepository.findByNameLikeIgnoreCaseOrDescriptionLikeIgnoreCaseAndAvailableOrderByIdDesc(
                text, true, pageRequest)) {
            UserDto userDto = userMapper.toUserDto(item.getOwner());
            ItemDto itemDto = itemMapper.toItemDto(item, userDto);
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }

    @Override
    @Transactional
    public CommentResponseDto addComment(Long userId, Long itemId, CommentRequestDto commentRequestDto) {
        User user = getUser(userId);
        Item item = getItem(itemId);

        if (!bookingRepository.existsByItemIdAndBookerIdAndEndLessThanAndStatus(itemId, userId, DateUtils.getCurrentTime(),
                BookingStatus.APPROVED)) {
            throw new EntityNotValidException("item", "bookings");
        }

        return commentMapper.toResponseDto(commentRepository.save(
                commentMapper.toComment(commentRequestDto, item, user, DateUtils.getCurrentTime())), userMapper.toUserDto(user));
    }

    private User getUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user", userId));
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("item", itemId));
    }

    private void validateOwner(Long userId, Item item) {
        if (!Objects.equals(item.getOwner().getId(), userId)) {
            throw new EntityNotValidException("user", "id");
        }
    }

    private void setBookings(ItemDto itemDto, Long requestUserId, List<Booking> bookings, LocalDateTime now) {
        if (Objects.equals(itemDto.getOwner().getId(), requestUserId)) {
            Booking lastBooking = bookings == null ? null : bookings.stream().filter(booking -> !booking.getStart().isAfter(now)).reduce((first, second) -> second).orElse(null);

            Booking nextBooking = bookings == null ? null : bookings.stream().filter(booking -> booking.getStart().isAfter(now)).findFirst().orElse(null);

            itemDto.setLastBooking(Objects.isNull(lastBooking) ? null : bookingMapper.toItemResponseDto(lastBooking, userMapper.toUserDto(lastBooking.getBooker())));
            itemDto.setNextBooking(Objects.isNull(nextBooking) ? null : bookingMapper.toItemResponseDto(nextBooking, userMapper.toUserDto(nextBooking.getBooker())));
        }
    }

    private void setComments(ItemDto itemDto, List<Comment> comments) {
        itemDto.setComments(comments == null ? List.of() : comments.stream().map(comment -> commentMapper.toResponseDto(comment, userMapper.toUserDto(comment.getAuthor()))).collect(toList()));
    }

    private Map<Item, List<Comment>> getComments(List<Item> items) {
        return commentRepository.findByItemIn(items, Sort.by(Sort.Direction.DESC, "created"))
                .stream()
                .collect(Collectors.groupingBy(Comment::getItem, toList()));
    }

    private Map<Item, List<Booking>> getBookings(List<Item> items) {
        return bookingRepository.findByItemInAndStatus(items,
                        Sort.by(Sort.Direction.ASC, "start"), BookingStatus.APPROVED)
                .stream()
                .collect(Collectors.groupingBy(Booking::getItem, toList()));
    }
}