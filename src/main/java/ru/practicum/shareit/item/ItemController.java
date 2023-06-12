package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentRequestDto;
import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.utils.CommonConstants;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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
                              @Validated(ItemRequestDto.New.class) @RequestBody ItemRequestDto commentRequestDto) {
        log.info("POST-items was called.");
        return itemService.createItem(commentRequestDto, userId);
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
                              @Validated(ItemRequestDto.Update.class) @RequestBody ItemDto itemDto,
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
    public List<ItemDto> getAllAvailableItemsByText(@RequestParam String text,
                                                    @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                                    @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                    @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("GET-items (available) was called.");
        return itemService.getAvailableItemsByText(text, userId, PageRequest.of(from, size));
    }

    @PostMapping("/{itemId}/comment")
    @SuppressWarnings(value = "unused")
    public CommentResponseDto addComment(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @PathVariable long itemId,
            @Valid @RequestBody CommentRequestDto commentRequestDto
    ) {
        log.info("POST-items (comment) was called.");
        return itemService.addComment(userId, itemId, commentRequestDto);
    }
}
