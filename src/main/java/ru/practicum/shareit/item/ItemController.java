package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.RequestDto;
import ru.practicum.shareit.comment.dto.ResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.utils.CommonConstants;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @SuppressWarnings(value = "unused")
    public ItemDto createItem(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                              @RequestBody ItemDto itemDto) {
        log.info("POST-items was called.");
        return itemService.createItem(itemDto, userId);
    }

    @GetMapping("/{itemId}")
    @SuppressWarnings(value = "unused")
    public ItemDto readItem(@PathVariable long itemId,
                            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long requesterId) {
        log.info("GET-items was called.");
        return itemService.readItem(itemId, requesterId);
    }

    @PatchMapping("/{itemId}")
    @SuppressWarnings(value = "unused")
    public ItemDto updateItem(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                              @RequestBody ItemDto itemDto,
                              @PathVariable long itemId) {
        log.info("PATCH-items was called.");
        return itemService.updateItem(userId, itemDto, itemId);
    }

    @DeleteMapping("/{itemId}")
    @SuppressWarnings(value = "unused")
    public void deleteItem(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                           @PathVariable long itemId) {
        log.info("DELETE-items was called.");
        itemService.deleteItem(userId, itemId);
    }

    @GetMapping
    @SuppressWarnings(value = "unused")
    public List<ItemDto> getAllItems(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId) {
        log.info("GET-items (all) was called.");
        return itemService.getAllItems(userId);
    }

    @GetMapping("/search")
    @SuppressWarnings(value = "unused")
    public List<ItemDto> getAllAvailableItemsByText(@RequestParam String text) {
        log.info("GET-items (available) was called.");
        return itemService.getAvailableItemsByText(text);
    }

    @PostMapping("/{itemId}/comment")
    @SuppressWarnings(value = "unused")
    public ResponseDto addComment(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @PathVariable long itemId,
            @Valid @RequestBody RequestDto commentRequestDto
    ) {
        log.info("POST-items (comment) was called.");
        return itemService.addComment(userId, itemId, commentRequestDto);
    }
}
