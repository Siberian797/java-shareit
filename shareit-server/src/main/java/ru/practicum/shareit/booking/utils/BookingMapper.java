package ru.practicum.shareit.booking.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.dto.UserDto;

@Component
public class BookingMapper {
    public BookingResponseDto toResponseDto(Booking booking, UserDto userDto, ru.practicum.shareit.item.dto.ItemDto itemDto) {
        return BookingResponseDto.builder()
                .id(booking.getId())
                .status(booking.getStatus())
                .start(booking.getStart())
                .end(booking.getEnd())
                .booker(userDto)
                .item(itemDto)
                .build();
    }

    public BookingDto toItemResponseDto(Booking booking, UserDto userDto) {
        return BookingDto.builder()
                .id(booking.getId())
                .status(booking.getStatus())
                .start(booking.getStart())
                .end(booking.getEnd())
                .bookerId(userDto.getId())
                .build();
    }

    public Booking toBooking(BookingRequestDto bookingRequestDto) {
        return Booking.builder()
                .start(bookingRequestDto.getStart())
                .end(bookingRequestDto.getEnd())
                .build();
    }
}
