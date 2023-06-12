package ru.practicum.shareit.comment.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.comment.dto.CommentRequestDto;
import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CommentMapper.class})
@ExtendWith(SpringExtension.class)
class CommentMapperTest {
    @Autowired
    @SuppressWarnings("unused")
    private CommentMapper commentMapper;

    /**
     * Method under test: {@link CommentMapper#toResponseDto(Comment, UserDto)}
     */
    @Test
    void testToResponseDto() {
        User author = new User();
        author.setEmail("jane.doe@example.org");
        author.setId(1L);
        author.setName("Name");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        Request request = new Request();
        request.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setRequester(requester);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
        Comment comment = mock(Comment.class);
        when(comment.getId()).thenReturn(1);
        when(comment.getText()).thenReturn("Text");
        when(comment.getCreated()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(comment).setAuthor(Mockito.any());
        doNothing().when(comment).setCreated(Mockito.any());
        doNothing().when(comment).setId(Mockito.<Integer>any());
        doNothing().when(comment).setItem(Mockito.any());
        doNothing().when(comment).setText(Mockito.any());
        comment.setAuthor(author);
        comment.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        comment.setId(1);
        comment.setItem(item);
        comment.setText("Text");
        UserDto userDto = mock(UserDto.class);
        when(userDto.getName()).thenReturn("Name");
        CommentResponseDto actualToResponseDtoResult = commentMapper.toResponseDto(comment, userDto);
        assertEquals("Name", actualToResponseDtoResult.getAuthorName());
        assertEquals("Text", actualToResponseDtoResult.getText());
        assertEquals(1, actualToResponseDtoResult.getId().intValue());
        assertEquals("1970-01-01", actualToResponseDtoResult.getCreated().toLocalDate().toString());
        verify(comment).getId();
        verify(comment).getText();
        verify(comment).getCreated();
        verify(comment).setAuthor(Mockito.any());
        verify(comment).setCreated(Mockito.any());
        verify(comment).setId(Mockito.<Integer>any());
        verify(comment).setItem(Mockito.any());
        verify(comment).setText(Mockito.any());
        verify(userDto).getName();
    }

    /**
     * Method under test: {@link CommentMapper#toComment(CommentRequestDto, Item, User, LocalDateTime)}
     */
    @Test
    void testToComment() {
        CommentRequestDto commentRequestDto = new CommentRequestDto("Text");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        Request request = new Request();
        request.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setRequester(requester);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Comment actualToCommentResult = commentMapper.toComment(commentRequestDto, item, user,
                LocalDate.of(1970, 1, 1).atStartOfDay());
        assertSame(user, actualToCommentResult.getAuthor());
        assertEquals("Text", actualToCommentResult.getText());
        assertSame(item, actualToCommentResult.getItem());
        assertEquals("00:00", actualToCommentResult.getCreated().toLocalTime().toString());
        assertNull(actualToCommentResult.getId());
    }

    /**
     * Method under test: {@link CommentMapper#toComment(CommentRequestDto, Item, User, LocalDateTime)}
     */
    @Test
    void testToComment2() {
        CommentRequestDto commentRequestDto = mock(CommentRequestDto.class);
        when(commentRequestDto.getText()).thenReturn("Text");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        Request request = new Request();
        request.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setRequester(requester);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Comment actualToCommentResult = commentMapper.toComment(commentRequestDto, item, user,
                LocalDate.of(1970, 1, 1).atStartOfDay());
        assertSame(user, actualToCommentResult.getAuthor());
        assertEquals("Text", actualToCommentResult.getText());
        assertSame(item, actualToCommentResult.getItem());
        assertEquals("00:00", actualToCommentResult.getCreated().toLocalTime().toString());
        assertNull(actualToCommentResult.getId());
        verify(commentRequestDto).getText();
    }
}

