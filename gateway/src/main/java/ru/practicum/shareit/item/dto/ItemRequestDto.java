package ru.practicum.shareit.item.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    @NotBlank(groups = {New.class})
    @Size(groups = {Update.class, New.class}, min = 1)
    private String name;

    @NotBlank(groups = {New.class})
    @Size(groups = {Update.class, New.class}, min = 1)
    private String description;

    @NotNull(groups = {New.class})
    private Boolean available;

    private Long requestId;

    public interface New {
    }

    public interface Update {
    }
}
