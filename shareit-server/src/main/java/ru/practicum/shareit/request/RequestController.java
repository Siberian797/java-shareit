package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.utils.CommonConstants;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
@Slf4j
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    public RequestResponseDto createRequest(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId, @Valid @RequestBody RequestRequestDto requestDto) {
        log.info("POST-requests was called.");
        return requestService.createRequest(requestDto, userId);
    }

    @GetMapping("/{requestId}")
    public RequestResponseDto readRequest(@PathVariable Long requestId,
                                          @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId) {
        log.info("GET-request was called.");
        return requestService.readRequest(requestId, userId);
    }


    @GetMapping
    public List<RequestResponseDto> getAllRequestsByUser(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId) {
        log.info("GET-requests (all-by-user) was called.");
        return requestService.getAllRequestsByUser(userId);
    }

    @GetMapping("/all")
    public List<RequestResponseDto> getAllRequests(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("GET-requests (all) was called.");
        return requestService.getAllRequests(PageRequest.of(from, size), userId);
    }
}
