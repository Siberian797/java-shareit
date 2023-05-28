package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.shareit.booking.dto.RequestDto;
import ru.practicum.shareit.booking.dto.ResponseDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.status.BookingState;
import ru.practicum.shareit.utils.CommonConstants;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseDto createBooking(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @Valid @RequestBody RequestDto requestDto
    ) {
        return bookingService.createBooking(requestDto, userId);
    }

    @GetMapping("/{bookingId}")
    public ResponseDto readBooking(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @PathVariable Long bookingId
    ) {
        return bookingService.readBooking(bookingId, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseDto updateBooking(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @RequestParam(required = false) boolean approved,
            @PathVariable long bookingId
    ) {
        return bookingService.updateBooking(bookingId, approved, userId);
    }

    @GetMapping
    public List<ResponseDto> getUserBookings(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @RequestParam(required = false, defaultValue = "ALL") BookingState state
    ) {
        return bookingService.getBookings(state, userId);
    }

    @GetMapping("/owner")
    public List<ResponseDto> getUserItems(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @RequestParam(required = false, defaultValue = "ALL") BookingState state
    ) {
        return bookingService.getItems(state, userId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public Map<String, String> handleNotExistingFilm(final MethodArgumentTypeMismatchException e) {
        return Map.of("error", "Unknown " + e.getName() + ": " + e.getValue());
    }
}
