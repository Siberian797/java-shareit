package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.status.BookingState;
import ru.practicum.shareit.utils.CommonConstants;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @SuppressWarnings(value = "unused")
    public BookingResponseDto createBooking(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                            @Valid @RequestBody BookingRequestDto bookingRequestDto) {
        log.info("POST-bookings was called.");
        return bookingService.createBooking(bookingRequestDto, userId);
    }

    @GetMapping("/{bookingId}")
    @SuppressWarnings(value = "unused")
    public BookingResponseDto readBooking(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                          @PathVariable Long bookingId) {
        log.info("GET-bookings was called.");
        return bookingService.readBooking(bookingId, userId);
    }

    @PatchMapping("/{bookingId}")
    @SuppressWarnings(value = "unused")
    public BookingResponseDto updateBooking(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                            @RequestParam boolean approved,
                                            @PathVariable long bookingId) {
        log.info("PATCH-bookings was called.");
        return bookingService.updateBooking(bookingId, approved, userId);
    }

    @GetMapping
    @SuppressWarnings(value = "unused")
    public List<BookingResponseDto> getUserBookings(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                                    @RequestParam(defaultValue = "ALL") BookingState state,
                                                    @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                    @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("GET-user-bookings was called.");
        return bookingService.getBookings(state, userId, PageRequest.of(from, size));
    }

    @GetMapping("/owner")
    @SuppressWarnings(value = "unused")
    public List<BookingResponseDto> getUserItems(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                                 @RequestParam(defaultValue = "ALL") BookingState state,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                 @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("GET-owner-bookings was called.");
        return bookingService.getItems(state, userId, PageRequest.of(from, size));
    }
}