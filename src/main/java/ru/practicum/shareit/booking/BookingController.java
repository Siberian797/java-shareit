package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.RequestDto;
import ru.practicum.shareit.booking.dto.ResponseDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.status.BookingState;
import ru.practicum.shareit.utils.CommonConstants;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @SuppressWarnings(value = "unused")
    public ResponseDto createBooking(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @Valid @RequestBody RequestDto requestDto) {
        log.info("POST-bookings was called.");
        return bookingService.createBooking(requestDto, userId);
    }

    @GetMapping("/{bookingId}")
    @SuppressWarnings(value = "unused")
    public ResponseDto readBooking(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @PathVariable Long bookingId) {
        log.info("GET-bookings was called.");
        return bookingService.readBooking(bookingId, userId);
    }

    @PatchMapping("/{bookingId}")
    @SuppressWarnings(value = "unused")
    public ResponseDto updateBooking(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @RequestParam boolean approved,
            @PathVariable long bookingId) {
        log.info("PATCH-bookings was called.");
        return bookingService.updateBooking(bookingId, approved, userId);
    }

    @GetMapping
    @SuppressWarnings(value = "unused")
    public List<ResponseDto> getUserBookings(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @RequestParam(defaultValue = "ALL") BookingState state) {
        log.info("GET-user-bookings was called.");
        return bookingService.getBookings(state, userId);
    }

    @GetMapping("/owner")
    @SuppressWarnings(value = "unused")
    public List<ResponseDto> getUserItems(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @RequestParam(defaultValue = "ALL") BookingState state) {
        log.info("GET-owner-bookings was called.");
        return bookingService.getItems(state, userId);
    }
}