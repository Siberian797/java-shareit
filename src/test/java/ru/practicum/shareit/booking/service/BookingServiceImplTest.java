package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
    @Mock
    private BookingService bookingService;

    @Test
    void createBooking() {
        BookingResponseDto bookingRequestDto = bookingService.createBooking(BookingRequestDto.builder().build(), 1L);
        assertEquals(bookingRequestDto, bookingService.readBooking(1L, 1L));
    }

    @Test
    void readBooking() {
        BookingResponseDto bookingRequestDto = bookingService.createBooking(BookingRequestDto.builder().build(), 1L);
        assertEquals(bookingRequestDto, bookingService.readBooking(1L, 1L));
    }

    @Test
    void updateBooking() {
        bookingService.createBooking(BookingRequestDto.builder().build(), 1L);
        BookingResponseDto updated = bookingService.updateBooking(1L, true, 1L);

        assertEquals(updated, bookingService.readBooking(1L, 1L));
    }
}