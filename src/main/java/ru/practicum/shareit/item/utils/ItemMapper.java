package ru.practicum.shareit.item.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemMapper {
    public ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getItemId())
                .name(item.getItemName())
                .description(item.getItemDescription())
                .available(item.isItemAvailable())
                .request(item.getItemRequest())
                .build();
    }

    public Item toItem(ItemDto itemDto) {
        return Item.builder()
                .itemId(itemDto.getId())
                .itemName(itemDto.getName())
                .itemDescription(itemDto.getDescription())
                .isItemAvailable(itemDto.isAvailable())
                .build();
    }
}
