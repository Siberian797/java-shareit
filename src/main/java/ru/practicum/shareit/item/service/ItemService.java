package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto, long userId);
    ItemDto readItem(long itemId);
    ItemDto updateItem(long userId, ItemDto itemDto, long itemId);
    boolean deleteItem(long userId, long itemId);

    List<ItemDto> getAllItems();
    List<ItemDto> getAllAvailableItemsByText(String text);
}
