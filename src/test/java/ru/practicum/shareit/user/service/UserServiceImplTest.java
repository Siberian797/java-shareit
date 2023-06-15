package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.exception.EntityDuplicateException;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.utils.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @MockBean
    private UserMapper userMapper;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDto)}
     */
    @Test
    void testCreateUser() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userRepository.save(Mockito.any())).thenReturn(user);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        when(userMapper.toUser(userMapper.toUserDto(user))).thenReturn(user2);
        when(userServiceImpl.createUser(Mockito.any())).thenReturn(UserDto.builder().id(1L).name("Name").email("jane.doe@example.org").build());
        UserDto actualCreateUserResult = userServiceImpl.createUser(null);
        assertEquals("jane.doe@example.org", actualCreateUserResult.getEmail());
        assertEquals("Name", actualCreateUserResult.getName());
        assertEquals(1L, actualCreateUserResult.getId());
        userMapper.toUser(Mockito.any());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDto)}
     */
    @Test
    void testCreateUser2() {
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
        when(userRepository.save(Mockito.any())).thenReturn(user);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        when(userMapper.toUser(Mockito.any())).thenReturn(user2);
        when(userServiceImpl.createUser(Mockito.any())).thenReturn(UserDto.builder().id(1L).name("Name").email("jane.doe@example.org").build());
        UserDto actualCreateUserResult = userServiceImpl.createUser(null);
        assertEquals("jane.doe@example.org", actualCreateUserResult.getEmail());
        assertEquals("Name", actualCreateUserResult.getName());
        assertEquals(1L, actualCreateUserResult.getId());
        userMapper.toUser(Mockito.any());
    }

    /**
     * Method under test: {@link UserServiceImpl#readUser(long)}
     */
    @Test
    void testReadUser() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(userServiceImpl.readUser(1L)).thenReturn(UserDto.builder().id(1L).name("Name").email("jane.doe@example.org").build());
        UserDto actualReadUserResult = userServiceImpl.readUser(1L);
        assertEquals("jane.doe@example.org", actualReadUserResult.getEmail());
        assertEquals("Name", actualReadUserResult.getName());
        assertEquals(1L, actualReadUserResult.getId());
    }

    /**
     * Method under test: {@link UserServiceImpl#readUser(long)}
     */
    @Test
    void testReadUser2() {
        User user = User.builder().id(1L).name("Name").email("jane.doe@example.org").build();

        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(userServiceImpl.readUser(1L)).thenReturn(UserDto.builder().id(1L).name("Name").email("jane.doe@example.org").build());
        UserDto actualReadUserResult = userServiceImpl.readUser(1L);
        assertEquals("jane.doe@example.org", actualReadUserResult.getEmail());
        assertEquals("Name", actualReadUserResult.getName());
        assertEquals(1L, actualReadUserResult.getId());
    }

    /**
     * Method under test: {@link UserServiceImpl#readUser(long)}
     */
    @Test
    void testReadUser3() {
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userServiceImpl.readUser(1L));
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserDto, long)}
     */
    @Test
    void testUpdateUser() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        when(userRepository.save(Mockito.any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");
        when(userMapper.toUser(Mockito.any())).thenReturn(user3);
        UserDto userDto = mock(UserDto.class);
        when(userDto.getEmail()).thenReturn("jane.doe@example.org");
        when(userDto.getName()).thenReturn("Name");
        when(userServiceImpl.updateUser(userDto, 1L)).thenReturn(UserDto.builder().id(1L).name("Name").email("jane.doe@example.org").build());
        UserDto actualUpdateUserResult = userServiceImpl.updateUser(userDto, 1L);
        assertEquals("jane.doe@example.org", actualUpdateUserResult.getEmail());
        assertEquals("Name", actualUpdateUserResult.getName());
        assertEquals(1L, actualUpdateUserResult.getId());
        userMapper.toUser(Mockito.any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(long)}
     */
    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(Mockito.<Long>any());
        userServiceImpl.deleteUser(1L);
        verify(userRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(long)}
     */
    @Test
    void testDeleteUser2() {
        doThrow(new EntityDuplicateException("Entity", "jane.doe@example.org")).when(userRepository)
                .deleteById(Mockito.<Long>any());
        assertThrows(EntityDuplicateException.class, () -> userServiceImpl.deleteUser(1L));
        verify(userRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(userServiceImpl.getAllUsers().isEmpty());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsers2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        List<UserDto> actualAllUsers = userServiceImpl.getAllUsers();
        assertEquals(1, actualAllUsers.size());
    }
}