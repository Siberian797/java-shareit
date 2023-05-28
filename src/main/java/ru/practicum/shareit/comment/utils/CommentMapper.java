package ru.practicum.shareit.comment.utils;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.comment.dto.RequestDto;
import ru.practicum.shareit.comment.dto.ResponseDto;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.user.dto.UserDto;

@Component
public class CommentMapper {
    public ResponseDto toResponseDto(Comment comment, UserDto authorDTO) {
        return ResponseDto.builder()
                .id(comment.getId())
                .created(comment.getCreated())
                .text(comment.getText())
                .authorName(authorDTO.getName())
                .build();
    }

    public Comment toComment(RequestDto requestDto) {
        return Comment.builder()
                .text(requestDto.getText())
                .build();
    }
}