package ru.practicum.shareit.booking.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.ItemDto;
import ru.practicum.shareit.booking.dto.RequestDto;
import ru.practicum.shareit.booking.dto.ResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.dto.UserDto;

@Component
public class BookingMapper {
    public ResponseDto toResponseDto(Booking booking, UserDto userDto, ru.practicum.shareit.item.dto.ItemDto itemDTO) {
        return ResponseDto.builder()
                .id(booking.getId())
                .status(booking.getStatus())
                .start(booking.getStart())
                .end(booking.getEnd())
                .booker(userDto)
                .item(itemDTO)
                .build();
    }

    public ItemDto toItemResponseDto(Booking booking, UserDto userDto) {
        return ItemDto.builder()
                .id(booking.getId())
                .status(booking.getStatus())
                .start(booking.getStart())
                .end(booking.getEnd())
                .bookerId(userDto.getId())
                .build();
    }

    public Booking toBooking(RequestDto requestDto) {
        return Booking.builder()
                .start(requestDto.getStart())
                .end(requestDto.getEnd())
                .build();
    }
}
