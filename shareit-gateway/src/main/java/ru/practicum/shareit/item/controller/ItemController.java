package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentRequestDto;
import ru.practicum.shareit.item.client.ItemClient;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.utils.CommonConstants;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                             @Validated(ItemRequestDto.New.class) @RequestBody ItemRequestDto commentRequestDto) {
        log.info("POST-items was called.");
        return itemClient.createItem(commentRequestDto, userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> readItem(@PathVariable long itemId,
                            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long requesterId) {
        log.info("GET-items was called.");
        return itemClient.readItem(itemId, requesterId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                              @Validated(ItemRequestDto.Update.class) @RequestBody ItemDto itemDto,
                              @PathVariable long itemId) {
        log.info("PATCH-items was called.");
        return itemClient.updateItem(userId, itemDto, itemId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                           @PathVariable long itemId) {
        log.info("DELETE-items was called.");
        itemClient.deleteItem(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItems(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                              @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                              @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("GET-items (all) was called.");
        return itemClient.getAllItems(userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getAllAvailableItemsByText(@RequestParam String text,
                                                    @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
                                                    @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                    @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("GET-items (available) was called.");
        return itemClient.getAvailableItemsByText(userId, text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @PathVariable long itemId,
            @Valid @RequestBody CommentRequestDto commentRequestDto
    ) {
        log.info("POST-items (comment) was called.");
        return itemClient.addComment(userId, itemId, commentRequestDto);
    }
}
