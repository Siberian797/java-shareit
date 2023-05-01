package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.model.ItemRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private long itemId;
    @NotBlank
    private String itemName;
    @NotBlank
    private String itemDescription;
    @NotNull
    private boolean isItemAvailable;
    private long itemOwner;
    private ItemRequest itemRequest;
}
