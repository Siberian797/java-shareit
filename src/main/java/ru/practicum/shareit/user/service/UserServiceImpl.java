package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.utils.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        return userMapper.toUserDto(userRepository.createUser(userMapper.toUser(userDto)));
    }

    @Override
    public UserDto readUser(long userId) {
        return userMapper.toUserDto(userRepository.readUser(userId));
    }

    @Override
    public UserDto updateUser(UserDto userDto, long userId) {
        return userMapper.toUserDto(userRepository.updateUser(userId, userMapper.toUser(userDto)));
    }

    @Override
    public boolean deleteUser(long userId) {
        return userRepository.deleteUser(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers().stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }
}
