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
import ru.practicum.shareit.utils.DateUtils;

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
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    @Override
    public RequestResponseDto createRequest(RequestRequestDto requestDto, long userId) {
        User user = checkAndReturnUser(userId);
        Request itemRequest = RequestMapper.toItemRequest(requestDto);

        itemRequest.setCreatedTime(DateUtils.getCurrentTime());
        itemRequest.setRequester(user);

        return RequestMapper.toResponseDto(
                requestRepository.save(itemRequest), userMapper.toUserDto(user), new ArrayList<>()
        );
    }

    @Override
    public RequestResponseDto readRequest(long requestId, long userId) {
        checkAndReturnUser(userId);
        Request itemRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("request", requestId));

        return RequestMapper.toResponseDto(
                itemRequest, userMapper.toUserDto(itemRequest.getRequester()), getItems(requestId)
        );
    }

    @Override
    public List<RequestResponseDto> getAllRequests(PageRequest pageRequest, long userId) {
        checkAndReturnUser(userId);

        List<Request> itemRequests = requestRepository.findByRequesterIdNot(userId, pageRequest);

        Map<Long, List<ItemDto>> itemsMap =
                getItemsMapIdToItemDtoList(itemRequests.stream().map(Request::getId).collect(Collectors.toSet()));

        return itemRequests.stream().map(itemRequest -> RequestMapper.toResponseDto(itemRequest, userMapper.toUserDto(itemRequest.getRequester()), itemsMap.get(itemRequest.getId()))
        ).collect(Collectors.toList());
    }

    @Override
    public List<RequestResponseDto> getAllRequestsByUser(long userId) {
        User user = checkAndReturnUser(userId);
        List<Request> itemRequests = requestRepository.findByRequesterIdOrderByCreatedTimeAsc(userId);

        Map<Long, List<ItemDto>> itemsMap =
                getItemsMapIdToItemDtoList(itemRequests.stream().map(Request::getId).collect(Collectors.toSet()));

        return itemRequests.stream().map(itemRequest -> RequestMapper.toResponseDto(itemRequest, userMapper.toUserDto(user), itemsMap.getOrDefault(itemRequest.getId(), List.of()))).collect(Collectors.toList());
    }

    private User checkAndReturnUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user", userId));
    }

    private List<ItemDto> getItems(long requestId) {
        return itemRepository.findByRequestIdOrderByRequestCreatedTimeAsc(requestId).stream()
                .map(item -> itemMapper.toItemDto(item, userMapper.toUserDto(item.getOwner())))
                .collect(Collectors.toList());
    }

    private Map<Long, List<ItemDto>> getItemsMapIdToItemDtoList(Set<Long> requestIds) {
        return itemRepository.findByRequestIdInOrderByRequestCreatedTimeAsc(requestIds)
                .stream()
                .map(item -> itemMapper.toItemDto(item, userMapper.toUserDto(item.getOwner())))
                .collect(Collectors.groupingBy(ItemDto::getRequestId));
    }
}
