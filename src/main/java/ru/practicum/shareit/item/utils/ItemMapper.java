package ru.practicum.shareit.item.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemMapper {
    public ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .itemDescription(item.getItemDescription())
                .isItemAvailable(item.isItemAvailable())
                .itemRequest(item.getItemRequest())
                .build();
    }

    public Item toItem(ItemDto itemDto) {
        return Item.builder()
                .itemId(itemDto.getItemId())
                .itemName(itemDto.getItemName())
                .itemDescription(itemDto.getItemDescription())
                .isItemAvailable(itemDto.isItemAvailable())
                .build();
    }
}
