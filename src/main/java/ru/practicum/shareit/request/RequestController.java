package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
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
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    @SuppressWarnings(value = "unused")
    public RequestResponseDto createRequest(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId, @Valid @RequestBody RequestRequestDto requestDto) {
        return requestService.createRequest(requestDto, userId);
    }

    @GetMapping("/{requestId}")
    @SuppressWarnings(value = "unused")
    public RequestResponseDto readRequest(@PathVariable Long requestId,
                                          @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId) {
        return requestService.readRequest(requestId, userId);
    }


    @GetMapping
    @SuppressWarnings(value = "unused")
    public List<RequestResponseDto> getAllRequestsByUser(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId) {
        return requestService.getAllRequestsByUser(userId);
    }

    @GetMapping("/all")
    @SuppressWarnings(value = "unused")
    public List<RequestResponseDto> getAllRequests(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        return requestService.getAllRequests(PageRequest.of(from, size), userId);
    }
}
