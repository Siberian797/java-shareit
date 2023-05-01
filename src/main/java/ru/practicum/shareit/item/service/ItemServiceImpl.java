package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public class ItemServiceImpl implements ItemService {
    @Override
    public ItemDto createItem(ItemDto itemDto, long userId) {
        return null;
    }

    @Override
    public ItemDto readItem(long itemId) {
        return null;
    }

    @Override
    public ItemDto updateItem(long userId, ItemDto itemDto, long itemId) {
        return null;
    }

    @Override
    public boolean deleteItem(long userId, long itemId) {
        return false;
    }

    @Override
    public List<ItemDto> getAllItems() {
        return null;
    }

    @Override
    public List<ItemDto> getAllAvailableItemsByText(String text) {
        return null;
    }
}
