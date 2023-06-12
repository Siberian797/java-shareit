package ru.practicum.shareit.request.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;

import java.util.List;

public interface RequestService {
    RequestResponseDto createRequest(RequestRequestDto requestDto,  long userId);

    RequestResponseDto readRequest(long requestId, long userId);

    List<RequestResponseDto> getAllRequests(PageRequest pageRequest, long userId);

    List<RequestResponseDto> getAllRequestsByUser(long userId);
}
