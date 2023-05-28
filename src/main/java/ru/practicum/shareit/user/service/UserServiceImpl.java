package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.EmailDuplicateException;
import ru.practicum.shareit.exception.EmailNotValidException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.utils.UserMapper;
import ru.practicum.shareit.utils.CommonConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final Map<Long, String> emails = new HashMap<>();
    private int idCounter = 1;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        try {
            User newUser = userRepository.save(userMapper.toUser(userDto));
            return userMapper.toUserDto(newUser);
        } catch (Exception e) {
            throw new EmailDuplicateException(userDto.getEmail());
        }

    }

    @Override
    public UserDto readUser(long userId) {
        return userMapper.toUserDto(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)));
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, long userId) {
        User oldUser =
                userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException(userId));

        User updatedUser = User.builder()
                .id(userId)
                .name(Objects.requireNonNullElse(userDto.getName(), oldUser.getName()))
                .email(Objects.requireNonNullElse(userDto.getEmail(), oldUser.getEmail()))
                .build();

        return createUser(userMapper.toUserDto(updatedUser));
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        emails.remove(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    private static boolean validate(String emailStr) {
        Matcher matcher = CommonConstants.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    private int getUniqueUserId() {
        return idCounter++;
    }
}
