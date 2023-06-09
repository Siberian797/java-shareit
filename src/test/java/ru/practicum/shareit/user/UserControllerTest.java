package ru.practicum.shareit.user;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;
import ru.practicum.shareit.user.utils.UserMapper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @MockBean
    private UserMapper userMapper;
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#createUser(UserDto)}
     */
    @Test
    void testCreateUser() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.any())).thenReturn(user);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        UserMapper userMapper = mock(UserMapper.class);
        when(userMapper.toUser(Mockito.any())).thenReturn(user2);
        UserDto userDto = UserDto.builder().id(1L).name("Name").email("jane.doe@example.org").build();
        when(userController.createUser(userDto)).thenReturn(userDto);
        UserDto actualCreateUserResult = userController.createUser(userDto);
        assertEquals("jane.doe@example.org", actualCreateUserResult.getEmail());
        assertEquals("Name", actualCreateUserResult.getName());
        assertEquals(1L, actualCreateUserResult.getId());
    }

    /**
     * Method under test: {@link UserController#readUser(long)}
     */
    @Test
    @SneakyThrows
    void testReadUser() {
        UserDto userDto = UserDto.builder().id(1L).build();
        when(userService.readUser(anyLong())).thenReturn(userDto);
        assertEquals(userDto, userController.readUser(1L));
    }

    /**
     * Method under test: {@link UserController#deleteUser(long)}
     */
    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{userId}", 1L);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#deleteUser(long)}
     */
    @Test
    void testDeleteUser2() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{userId}", 1L);
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue((new UserController(new UserServiceImpl(userRepository, new UserMapper()))).getAllUsers().isEmpty());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserController#updateUser(UserDto, long)}
     */
    @Test
    @SneakyThrows
    void testUpdateUser() {
        UserDto userDto = UserDto.builder().id(1L).build();
        when(userService.updateUser(userDto, 1L)).thenReturn(userDto);
        assertEquals(userDto, userController.updateUser(userDto, 1L));
    }
}

