package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item createItem(Item item, long userId);

    Item readItem(long itemId);

    Item updateItem(long userId, Item item, long itemId);

    boolean deleteItem(long userId, long itemId);

    List<Item> getAllItems(long UserId);

    List<Item> getAllAvailableItemsByText(String text);
}
