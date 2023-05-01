package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {
    User createUser(User user);
    User readUser(long userId);
    User updateUser(long userId, User user);
    boolean deleteUser(long userId);

    List<User> getAllUsers();
}
