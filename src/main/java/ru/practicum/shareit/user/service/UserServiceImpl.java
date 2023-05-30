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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userDto.getEmail() == null)
            throw new EmailNotValidException("null");
        validateEmail(userDto.getEmail());
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
                .name(userDto.getName() == null ? oldUser.getName()
                        : userDto.getName().isBlank() ? oldUser.getName()
                        : userDto.getName())
                .email(userDto.getEmail() == null ? oldUser.getEmail()
                        : userDto.getEmail().isBlank() ? oldUser.getEmail()
                        : userDto.getEmail())
                .build();

        return createUser(userMapper.toUserDto(updatedUser));
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    private static void validateEmail(String emailStr) {
        Matcher matcher = Pattern.compile(CommonConstants.VALID_EMAIL_ADDRESS_REGEX).matcher(emailStr);
        if (!matcher.matches()) {
            throw new EmailNotValidException(emailStr);
        }
    }
}
