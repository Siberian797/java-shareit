package ru.practicum.shareit.booking.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.status.BookingState;

import java.util.List;

public interface BookingService {
    BookingResponseDto createBooking(BookingRequestDto bookingRequestDto, long itemId);

    BookingResponseDto readBooking(long bookingId, long itemId);

    BookingResponseDto updateBooking(long bookingId, boolean approved, long itemId);

    List<BookingResponseDto> getBookings(BookingState state, long itemId, PageRequest pageRequest);

    List<BookingResponseDto> getItems(BookingState state, long itemId, PageRequest pageRequest);
}
