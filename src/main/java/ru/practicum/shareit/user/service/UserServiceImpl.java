package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.EntityDuplicateException;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.utils.UserMapper;

import java.util.List;
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
        try {
            User newUser = userRepository.save(userMapper.toUser(userDto));
            return UserMapper.toUserDto(newUser);
        } catch (EntityDuplicateException e) {
            throw new EntityDuplicateException("user", userDto.getEmail());
        }
    }

    @Override
    public UserDto readUser(long userId) {
        return UserMapper.toUserDto(userRepository.findById(userId).orElseThrow(()
                -> new EntityNotFoundException("user", userId)));
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, long userId) {
        User oldUser =
                userRepository.findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("user", userId));

        User updatedUser = User.builder()
                .id(userId)
                .name(userDto.getName() == null || userDto.getName().isBlank() ? oldUser.getName() : userDto.getName())
                .email(userDto.getEmail() == null || userDto.getEmail().isBlank() ? oldUser.getEmail() : userDto.getEmail())
                .build();

        return createUser(UserMapper.toUserDto(updatedUser));
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}
