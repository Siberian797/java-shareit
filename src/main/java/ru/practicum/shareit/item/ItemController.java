package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.utils.CommonConstants;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                              @RequestBody ItemDto itemDto) {
        log.info("POST-items was called.");
        return itemService.createItem(itemDto, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto readItem(@PathVariable long itemId) {
        log.info("GET-items was called.");
        return itemService.readItem(itemId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                              @RequestBody ItemDto itemDto,
                              @PathVariable long itemId) {
        log.info("PATCH-items was called.");
        return itemService.updateItem(userId, itemDto, itemId);
    }

    @DeleteMapping("/{itemId}")
    public boolean deleteItem(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                              @PathVariable long itemId) {
        log.info("DELETE-items was called.");
        return itemService.deleteItem(userId, itemId);
    }

    @GetMapping
    public List<ItemDto> getAllItems(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId) {
        log.info("GET-items (all) was called.");
        return itemService.getAllItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getAllAvailableItemsByText(@RequestParam String text) {
        log.info("GET-items (available) was called.");
        return itemService.getAllAvailableItemsByText(text);
    }
}
