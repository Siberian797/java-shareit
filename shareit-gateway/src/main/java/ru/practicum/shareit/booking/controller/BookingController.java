package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.client.BookingClient;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.status.BookingState;
import ru.practicum.shareit.utils.CommonConstants;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> createBooking(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                            @Valid @RequestBody BookingRequestDto bookingRequestDto) {
        log.info("POST-bookings was called.");
        return bookingClient.bookItem(bookingRequestDto, userId);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> readBooking(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                          @PathVariable Long bookingId) {
        log.info("GET-bookings was called.");
        return bookingClient.getBooking(bookingId, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> updateBooking(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                            @RequestParam boolean approved,
                                            @PathVariable long bookingId) {
        log.info("PATCH-bookings was called.");
        return bookingClient.updateBooking(bookingId, approved, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserBookings(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                                                   @RequestParam(defaultValue = "ALL") BookingState state,
                                                                   @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                                   @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("GET-user-bookings was called.");
        return bookingClient.getBookings(userId, state, from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getUserItems(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                                 @RequestParam(defaultValue = "ALL") BookingState state,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                 @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("GET-owner-bookings was called.");
        return bookingClient.getBookings(userId, state, from, size);
    }
}