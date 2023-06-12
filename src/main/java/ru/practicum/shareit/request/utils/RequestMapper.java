package ru.practicum.shareit.request.utils;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@UtilityClass
public class RequestMapper {
    public static RequestResponseDto toResponseDto(
            Request itemRequest, UserDto requester, List<ItemDto> items
    ) {
        return RequestResponseDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreatedTime())
                .requester(requester)
                .items(items)
                .build();
    }

    public static Request toItemRequest(RequestRequestDto itemRequestRequestDto) {
        return Request.builder()
                .description(itemRequestRequestDto.getDescription())
                .build();
    }
}
