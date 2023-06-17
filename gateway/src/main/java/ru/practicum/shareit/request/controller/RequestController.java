package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.client.RequestClient;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.utils.CommonConstants;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
@Slf4j
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> createRequest(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId, @Valid @RequestBody RequestRequestDto requestDto) {
        log.info("POST-requests was called.");
        return requestClient.createRequest(requestDto, userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> readRequest(@PathVariable Long requestId,
                                          @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId) {
        log.info("GET-request was called.");
        return requestClient.readRequest(requestId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllRequestsByUser(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId) {
        log.info("GET-requests (all-by-user) was called.");
        return requestClient.getAllRequestsByUser(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("GET-requests (all) was called.");
        return requestClient.getAllRequests(userId, from, size);
    }
}
