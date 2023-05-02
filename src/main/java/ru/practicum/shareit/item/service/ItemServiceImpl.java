package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.utils.ItemMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;


    @Override
    public ItemDto createItem(ItemDto itemDto, long userId) {
        return itemMapper.toItemDto(itemRepository.createItem(itemMapper.toItem(itemDto), userId));
    }

    @Override
    public ItemDto readItem(long itemId) {
        return itemMapper.toItemDto(itemRepository.readItem(itemId));
    }

    @Override
    public ItemDto updateItem(long userId, ItemDto itemDto, long itemId) {
        return itemMapper.toItemDto(itemRepository.updateItem(userId, itemMapper.toItem(itemDto), itemId));
    }

    @Override
    public boolean deleteItem(long userId, long itemId) {
        return itemRepository.deleteItem(userId, itemId);
    }

    @Override
    public List<ItemDto> getAllItems(long userId) {
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : itemRepository.getAllItems(userId)) {
            itemDtos.add(itemMapper.toItemDto(item));
        }
        return itemDtos;
    }

    @Override
    public List<ItemDto> getAllAvailableItemsByText(String text) {
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : itemRepository.getAllAvailableItemsByText(text)) {
            itemDtos.add(itemMapper.toItemDto(item));
        }
        return itemDtos;
    }
}
