package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentRequestDto;
import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.utils.CommonConstants;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemService itemService;

    @PostMapping
    @SuppressWarnings(value = "unused")
    public ItemDto createItem(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId, @RequestBody ItemRequestDto itemDto) {
        return itemService.createItem(itemDto, userId);
    }

    @GetMapping("/{itemId}")
    @SuppressWarnings(value = "unused")
    public ItemDto readItem(@PathVariable Long itemId,
                            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId) {
        return itemService.readItem(itemId, userId);
    }

    @PatchMapping("/{itemId}")
    @SuppressWarnings(value = "unused")
    public ItemDto updateItem(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                              @RequestBody ItemDto itemDto, @PathVariable Long itemId) {
        return itemService.updateItem(itemId, itemDto, userId);
    }

    @DeleteMapping("/{itemId}")
    @SuppressWarnings(value = "unused")
    public void deleteItem(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId, @PathVariable Long itemId) {
        itemService.deleteItem(userId, itemId);
    }

    @GetMapping
    @SuppressWarnings(value = "unused")
    public List<ItemDto> getAllItems(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId) {
        return itemService.getAllItems(userId);
    }

    @GetMapping("/search")
    @SuppressWarnings(value = "unused")
    public List<ItemDto> getAvailableItems(@RequestParam String text) {
        return itemService.getAvailableItemsByText(text);
    }

    @PostMapping("/{itemId}/comment")
    @SuppressWarnings(value = "unused")
    public CommentResponseDto addComment(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                         @PathVariable long itemId, @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return itemService.addComment(userId, itemId, commentRequestDto);
    }
}
