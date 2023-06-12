package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.service.ItemService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {
    @InjectMocks
    private ItemController itemController;

    @Mock
    @SuppressWarnings("unused")
    private ItemService itemService;

    @Test
    void createItem() {
        ItemDto itemDto = itemController.createItem(1L, ItemRequestDto.builder().build());
        assertEquals(itemDto, itemController.readItem(1L, 1L));
    }

    @Test
    void readItem() {
        ItemDto itemDto = itemController.createItem(1L, ItemRequestDto.builder().build());
        assertEquals(itemDto, itemController.readItem(1L, 1L));
    }

    @Test
    @SuppressWarnings("unused")
    void updateItem() {
        ItemDto itemDto = itemController.createItem(1L, ItemRequestDto.builder().build());
        ItemDto updated = itemController.updateItem(1L, ItemDto.builder().description("updated").build(), 1L);
        assertEquals(updated, itemController.readItem(1L, 1L));

    }

    @Test
    void deleteItem() {
        ItemDto itemDto = itemController.createItem(1L, ItemRequestDto.builder().build());
        assertEquals(itemDto, itemController.readItem(1L, 1L));

        itemController.deleteItem(1L, 1L);
        assertNull(itemController.readItem(1L, 1L));
    }
}