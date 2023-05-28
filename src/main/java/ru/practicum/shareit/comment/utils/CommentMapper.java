package ru.practicum.shareit.comment.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.comment.dto.RequestDto;
import ru.practicum.shareit.comment.dto.ResponseDto;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Component
public class CommentMapper {
    public ResponseDto toResponseDto(Comment comment, UserDto userDto) {
        return ResponseDto.builder()
                .id(comment.getId())
                .created(comment.getCreated())
                .text(comment.getText())
                .authorName(userDto.getName())
                .build();
    }

    public Comment toComment(RequestDto requestDto, Item item, User user, LocalDateTime time) {
        return Comment.builder()
                .text(requestDto.getText())
                .item(item)
                .author(user)
                .created(time)
                .build();
    }
}