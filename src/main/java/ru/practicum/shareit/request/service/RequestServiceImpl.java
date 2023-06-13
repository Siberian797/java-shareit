package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.utils.ItemMapper;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.request.utils.RequestMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.utils.UserMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final RequestRepository requestRepository;

    @Override
    public RequestResponseDto createRequest(RequestRequestDto requestDto, long userId) {
        User user = checkAndReturnUser(userId);
        Request itemRequest = RequestMapper.toItemRequest(requestDto);

        itemRequest.setCreatedTime(LocalDateTime.now());
        itemRequest.setRequester(user);

        return RequestMapper.toResponseDto(
                requestRepository.save(itemRequest), UserMapper.toUserDto(user), new ArrayList<>()
        );
    }

    @Override
    public RequestResponseDto readRequest(long requestId, long userId) {
        checkAndReturnUser(userId);
        Request itemRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("request", requestId));

        return RequestMapper.toResponseDto(
                itemRequest, UserMapper.toUserDto(itemRequest.getRequester()), getItems(requestId)
        );
    }

    @Override
    public List<RequestResponseDto> getAllRequests(PageRequest pageRequest, long userId) {
        checkAndReturnUser(userId);

        List<Request> itemRequests = requestRepository.findByRequesterIdNot(userId, pageRequest);

        Map<Long, List<ItemDto>> itemsMap =
                getItems(itemRequests.stream().map(Request::getId).collect(Collectors.toSet()));

        return itemRequests.stream().map(itemRequest -> RequestMapper.toResponseDto(itemRequest, UserMapper.toUserDto(itemRequest.getRequester()), itemsMap.get(itemRequest.getId()))
        ).collect(Collectors.toList());
    }

    @Override
    public List<RequestResponseDto> getAllRequestsByUser(long userId) {
        User user = checkAndReturnUser(userId);
        List<Request> itemRequests = requestRepository.findByRequesterIdOrderByCreatedTimeAsc(userId);

        Map<Long, List<ItemDto>> itemsMap =
                getItems(itemRequests.stream().map(Request::getId).collect(Collectors.toSet()));

        return itemRequests.stream().map(itemRequest -> RequestMapper.toResponseDto(itemRequest, UserMapper.toUserDto(user), itemsMap.getOrDefault(itemRequest.getId(), List.of()))).collect(Collectors.toList());
    }

    private User checkAndReturnUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user", userId));
    }

    private List<ItemDto> getItems(long requestId) {
        return itemRepository.findByRequestIdOrderByRequestCreatedTimeAsc(requestId).stream()
                .map(item -> ItemMapper.toItemDto(item, UserMapper.toUserDto(item.getOwner())))
                .collect(Collectors.toList());
    }

    private Map<Long, List<ItemDto>> getItems(Set<Long> requestIds) {
        return itemRepository.findByRequestIdInOrderByRequestCreatedTimeAsc(requestIds)
                .stream()
                .map(item -> ItemMapper.toItemDto(item, UserMapper.toUserDto(item.getOwner())))
                .collect(Collectors.groupingBy(ItemDto::getRequestId));
    }
}
