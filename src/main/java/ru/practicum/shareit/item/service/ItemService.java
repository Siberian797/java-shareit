package ru.practicum.shareit.item.service;

import ru.practicum.shareit.comment.dto.RequestDto;
import ru.practicum.shareit.comment.dto.ResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto, long userId);

    ItemDto readItem(long itemId, long userId);

    ItemDto updateItem(long userId, ItemDto itemDto, long itemId);

    void deleteItem(long userId, long itemId);

    List<ItemDto> getAllItems(long userId);

    List<ItemDto> getAvailableItemsByText(String text);

    ResponseDto addComment(Long userId, Long itemId, RequestDto requestDto);
}
