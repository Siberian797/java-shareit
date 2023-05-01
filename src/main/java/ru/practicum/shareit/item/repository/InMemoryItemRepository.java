package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ItemNotValidException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InMemoryItemRepository implements ItemRepository {
    private final Map<Long, Item> itemsMap = new HashMap<>();
    private final UserService userService;
    private Long idCounter = 1L;

    @Override
    public Item createItem(Item item, long userId) {
        validate(item);
        item.setOwner(userService.readUser(userId).getId());
        item.setId(getUniqueItemId());
        itemsMap.put(item.getId(), item);
        return item;
    }

    @Override
    public Item readItem(long itemId) {
        Item item = itemsMap.get(itemId);
        item.setOwner(userService.readUser(item.getOwner()).getId());
        return item;
    }

    @Override
    public Item updateItem(long userId, Item item, long itemId) {
        Item oldItem = itemsMap.get(itemId);
        item.setOwner(oldItem.getOwner());
        validate(userId, item, oldItem);
        itemsMap.put(itemId, item);
        return itemsMap.get(itemId);
    }

    @Override
    public boolean deleteItem(long userId, long itemId) {
        itemsMap.remove(itemId);
        return true;
    }

    @Override
    public List<Item> getAllItems(long userId) {
        UserDto userDto = userService.readUser(userId);

        return itemsMap.values().stream().filter(item -> item.getOwner() == userDto.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> getAllAvailableItemsByText(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        return itemsMap.values().stream().filter(Item::getAvailable).filter(item -> item.getName().toLowerCase()
                .contains(text.toLowerCase()) || item.getDescription().toLowerCase().contains(
                text.toLowerCase())).collect(Collectors.toList());
    }

    private Long getUniqueItemId() {
        return idCounter++;
    }

    private void validate(Item item) {
        if (Objects.equals(item.getAvailable(), null)) {
            throw new ItemNotValidException("available");
        }
        if (item.getName().isEmpty()) {
            throw new ItemNotValidException("name");
        }
        if (Objects.equals(item.getDescription(), null)) {
            throw new ItemNotValidException("description");
        }
    }

    private void validate(long userId, Item item, Item oldItem) {
        if (item.getName() == null) {
            item.setName(oldItem.getName());
        }
        if (item.getDescription() == null) {
            item.setDescription(oldItem.getDescription());
        }
        if (item.getAvailable() == null) {
            item.setAvailable(oldItem.getAvailable());
        }
        if (!Objects.equals(item.getId(), oldItem.getId())) {
            item.setId(oldItem.getId());
        }
        if (item.getOwner() != userId) {
            throw new UserNotFoundException(userId);
        }
    }
}
