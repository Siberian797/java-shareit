package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.RequestDto;
import ru.practicum.shareit.booking.dto.ResponseDto;
import ru.practicum.shareit.booking.status.BookingState;

import java.util.List;

public interface BookingService {
    ResponseDto createBooking(RequestDto requestDto, long itemId);

    ResponseDto readBooking(long bookingId, long itemId);

    ResponseDto updateBooking(long bookingId, boolean approved, long itemId);

    List<ResponseDto> getBookings(BookingState state, long itemId);

    List<ResponseDto> getItems(BookingState state, long itemId);
}
