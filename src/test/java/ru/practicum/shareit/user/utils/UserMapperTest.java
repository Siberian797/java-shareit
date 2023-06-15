package ru.practicum.shareit.user.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserMapper.class})
@ExtendWith(SpringExtension.class)
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    /**
     * Method under test: {@link UserMapper#toUserDto(User)}
     */
    @Test
    void testToUserDto() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserDto actualToUserDtoResult = userMapper.toUserDto(user);
        assertEquals("jane.doe@example.org", actualToUserDtoResult.getEmail());
        assertEquals("Name", actualToUserDtoResult.getName());
        assertEquals(1L, actualToUserDtoResult.getId());
    }

    /**
     * Method under test: {@link UserMapper#toUserDto(User)}
     */
    @Test
    void testToUserDto2() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        when(user.getEmail()).thenReturn("jane.doe@example.org");
        when(user.getName()).thenReturn("Name");
        doNothing().when(user).setEmail(Mockito.any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setName(Mockito.any());
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserDto actualToUserDtoResult = userMapper.toUserDto(user);
        assertEquals("jane.doe@example.org", actualToUserDtoResult.getEmail());
        assertEquals("Name", actualToUserDtoResult.getName());
        assertEquals(1L, actualToUserDtoResult.getId());
        verify(user).getId();
        verify(user).getEmail();
        verify(user).getName();
        verify(user).setEmail(Mockito.any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setName(Mockito.any());
    }

    /**
     * Method under test: {@link UserMapper#toUser(UserDto)}
     */
    @Test
    void testToUser() {
        UserDto userDto = mock(UserDto.class);
        when(userDto.getEmail()).thenReturn("jane.doe@example.org");
        when(userDto.getName()).thenReturn("Name");
        when(userDto.getId()).thenReturn(1L);
        User actualToUserResult = userMapper.toUser(userDto);
        assertEquals("jane.doe@example.org", actualToUserResult.getEmail());
        assertEquals("Name", actualToUserResult.getName());
        assertEquals(1L, actualToUserResult.getId().longValue());
        verify(userDto).getEmail();
        verify(userDto).getName();
        verify(userDto).getId();
    }
}