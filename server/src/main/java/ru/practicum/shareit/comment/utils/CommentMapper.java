package ru.practicum.shareit.comment.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.comment.dto.CommentRequestDto;
import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Component
public class CommentMapper {
    public CommentResponseDto toResponseDto(Comment comment, UserDto userDto) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .created(comment.getCreated())
                .text(comment.getText())
                .authorName(userDto.getName())
                .build();
    }

    public Comment toComment(CommentRequestDto commentRequestDto, Item item, User user, LocalDateTime time) {
        return Comment.builder()
                .text(commentRequestDto.getText())
                .item(item)
                .author(user)
                .created(time)
                .build();
    }
}