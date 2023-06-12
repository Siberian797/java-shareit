package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {
    @Mock
    private BookingController bookingController;

    @Test
    void createBooking() {
        BookingResponseDto bookingRequestDto = bookingController.createBooking(1L, BookingRequestDto.builder().build());
        assertEquals(bookingRequestDto, bookingController.readBooking(1L, 1L));
    }

    @Test
    void readBooking() {
        BookingResponseDto bookingRequestDto = bookingController.createBooking(1L, BookingRequestDto.builder().build());
        assertEquals(bookingRequestDto, bookingController.readBooking(1L, 1L));
    }

    @Test
    void updateBooking() {
        bookingController.createBooking(1L, BookingRequestDto.builder().build());
        BookingResponseDto updated = bookingController.updateBooking(1L, true, 1L);

        assertEquals(updated, bookingController.readBooking(1L, 1L));
    }
}