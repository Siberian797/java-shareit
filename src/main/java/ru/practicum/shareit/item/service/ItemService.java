package ru.practicum.shareit.item.service;

import ru.practicum.shareit.comment.dto.CommentRequestDto;
import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemRequestDto itemDto, long userId);

    ItemDto readItem(long itemId, long userId);

    ItemDto updateItem(long userId, ItemDto itemDto, long itemId);

    void deleteItem(long userId, long itemId);

    List<ItemDto> getAllItems(long userId);

    List<ItemDto> getAvailableItemsByText(String text);

    CommentResponseDto addComment(Long userId, Long itemId, CommentRequestDto commentRequestDto);
}
