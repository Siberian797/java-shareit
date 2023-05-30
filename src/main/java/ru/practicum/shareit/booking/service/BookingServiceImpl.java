package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.status.BookingState;
import ru.practicum.shareit.booking.status.BookingStatus;
import ru.practicum.shareit.booking.utils.BookingMapper;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.utils.ItemMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.utils.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingMapper bookingMapper;
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto, long userId) {
        User user = getValidUser(userId);
        Item item = itemRepository.findById(bookingRequestDto.getItemId()).orElseThrow(() -> new ItemNotFoundException(bookingRequestDto.getItemId()));

        if (Boolean.FALSE.equals(item.getAvailable())) {
            throw new ItemNotValidException("available");
        }

        if (bookingRequestDto.getStart().isAfter(bookingRequestDto.getEnd())) {
            throw new ItemNotValidException("start");
        }

        if (bookingRequestDto.getStart().equals(bookingRequestDto.getEnd())) {
            throw new ItemNotValidException("start");
        }

        if (item.getOwner().getId().equals(userId)) {
            throw new UserNotFoundException(userId);
        }

        Booking booking = bookingMapper.toBooking(bookingRequestDto);
        booking.setStatus(BookingStatus.WAITING);
        booking.setBooker(user);
        booking.setItem(item);

        Booking newBooking = bookingRepository.save(booking);

        return getBookingResponseDto(newBooking);
    }

    @Override
    public BookingResponseDto readBooking(long bookingId, long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId));

        if (booking.getBooker().getId().equals(userId) ||
                booking.getItem().getOwner().getId().equals(userId)) {
            return getBookingResponseDto(booking);
        }

        throw new BookingNotFoundException(bookingId);
    }

    @Override
    @Transactional
    public BookingResponseDto updateBooking(
            long bookingId, boolean approved, long userId
    ) {
        getValidUser(userId);
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId));

        if (!booking.getStatus().equals(BookingStatus.WAITING)) {
            throw new BookingNotValidException("status");
        }

        if (booking.getBooker().getId().equals(userId)) {
            throw new BookingNotFoundException(bookingId);
        }

        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new BookingNotValidException("item");
        }

        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);

        return getBookingResponseDto(bookingRepository.save(booking));
    }

    @Override
    public List<BookingResponseDto> getBookings(BookingState state, long userId) {
        getValidUser(userId);
        LocalDateTime currentTime = LocalDateTime.now();

        List<Booking> bookings;

        switch (state) {
            case CURRENT:
                bookings = bookingRepository.findByBookerIdAndStartLessThanAndEndGreaterThanOrderByStartDesc(userId,
                        currentTime, currentTime);
                break;
            case PAST:
                bookings = bookingRepository.findByBookerIdAndEndLessThanOrderByStartDesc(userId, currentTime);
                break;
            case FUTURE:
                bookings = bookingRepository.findByBookerIdAndStartGreaterThanOrderByStartDesc(userId, currentTime);
                break;
            case WAITING:
                bookings = bookingRepository.findByBookerIdAndStatusOrderByStartDesc(userId, BookingStatus.WAITING);
                break;
            case REJECTED:
                bookings = bookingRepository.findByBookerIdAndStatusOrderByStartDesc(userId, BookingStatus.REJECTED);
                break;
            default:
                bookings = bookingRepository.findByBookerIdOrderByStartDesc(userId);
                break;
        }

        return bookings.stream().map(this::getBookingResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDto> getItems(BookingState state, long ownerId) {
        getValidUser(ownerId);

        List<Booking> bookings;
        LocalDateTime currentTime = LocalDateTime.now();

        switch (state) {
            case CURRENT:
                bookings = bookingRepository.findByItemOwnerIdAndStartLessThanAndEndGreaterThanOrderByStartDesc(ownerId, currentTime, currentTime);
                break;
            case PAST:
                bookings = bookingRepository.findByItemOwnerIdAndEndLessThanOrderByStartDesc(ownerId, currentTime);
                break;
            case FUTURE:
                bookings = bookingRepository.findByItemOwnerIdAndStartGreaterThanOrderByStartDesc(ownerId, currentTime);
                break;
            case WAITING:
                bookings = bookingRepository.findByItemOwnerIdAndStatusOrderByStartDesc(ownerId, BookingStatus.WAITING);
                break;
            case REJECTED:
                bookings = bookingRepository.findByItemOwnerIdAndStatusOrderByStartDesc(ownerId, BookingStatus.REJECTED);
                break;
            default:
                bookings = bookingRepository.findByItemOwnerIdOrderByStartDesc(ownerId);
                break;
        }

        return bookings.stream().map(this::getBookingResponseDto).collect(Collectors.toList());
    }

    private User getValidUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    private BookingResponseDto getBookingResponseDto(Booking booking) {
        return bookingMapper.toResponseDto(booking, userMapper.toUserDto(booking.getBooker()),
                itemMapper.toItemDto(booking.getItem(), userMapper.toUserDto(booking.getItem().getOwner())));
    }
}
