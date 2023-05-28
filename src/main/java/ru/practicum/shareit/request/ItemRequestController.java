package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.RequestDto;
import ru.practicum.shareit.comment.dto.ResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;
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

    @GetMapping
    public List<ItemDto> getAllItems(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId) {
        return itemService.getAllItems(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(
            @PathVariable Long itemId,
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId
    ) {
        return itemService.readItem(itemId, userId);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ItemDto saveItem(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @RequestBody ItemDto itemDTO
    ) {
        return itemService.createItem(itemDTO, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @RequestBody ItemDto itemDTO,
            @PathVariable Long itemId
    ) {
        return itemService.updateItem(itemId, itemDTO, userId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId, @PathVariable Long itemId) {
        itemService.deleteItem(userId, itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchAvailableItems(@RequestParam String text) {
        return itemService.getAvailableItemsByText(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseDto addComment(
            @RequestHeader(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER) long userId,
            @PathVariable long itemId,
            @Valid @RequestBody RequestDto commentRequestDto
    ) {
        return itemService.addComment(userId, itemId, commentRequestDto);
    }
}
