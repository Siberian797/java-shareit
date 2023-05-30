package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    @SuppressWarnings("unused")
    public UserDto createUser(@Validated(UserDto.Create.class) @RequestBody UserDto userDto) {
        log.info("POST-users was called.");
        return userService.createUser(userDto);
    }

    @GetMapping("/{userId}")
    @SuppressWarnings("unused")
    public UserDto readUser(@PathVariable long userId) {
        log.info("GET-users was called.");
        return userService.readUser(userId);
    }

    @PatchMapping("/{userId}")
    @SuppressWarnings("unused")
    public UserDto updateUser(@Validated(UserDto.Update.class) @RequestBody UserDto userDto, @PathVariable long userId) {
        log.info("PATCH-users was called.");
        return userService.updateUser(userDto, userId);
    }

    @DeleteMapping("/{userId}")
    @SuppressWarnings("unused")
    public void deleteUser(@PathVariable long userId) {
        log.info("DELETE-users was called.");
        userService.deleteUser(userId);
    }

    @GetMapping
    @SuppressWarnings("unused")
    public List<UserDto> getAllUsers() {
        log.info("GET-users (all) was called.");
        return userService.getAllUsers();
    }
}
