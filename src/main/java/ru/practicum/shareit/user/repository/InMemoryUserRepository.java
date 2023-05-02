package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.EmailDuplicateException;
import ru.practicum.shareit.exception.EmailNotValidException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.utils.CommonConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> usersMap = new HashMap<>();
    private final Map<Long, String> emails = new HashMap<>();
    private int idCounter = 1;

    @Override
    public User createUser(User user) {
        if (emails.containsValue(user.getEmail())) {
            throw new EmailDuplicateException(user.getEmail());
        }
        if (user.getEmail() == null || !validate(user.getEmail())) {
            throw new EmailNotValidException(user.getEmail());
        }

        user.setId(getUniqueUserId());
        usersMap.put(user.getId(), user);
        emails.put(user.getId(), user.getEmail());

        return user;
    }

    @Override
    public User readUser(long userId) {
        User user = usersMap.get(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        return user;
    }

    @Override
    public User updateUser(long userId, User user) {
        user.setId(userId);
        if (emails.containsValue(user.getEmail()) && !usersMap.get(userId).getEmail().equals(user.getEmail())) {
            throw new EmailDuplicateException(user.getEmail());
        }
        if (user.getName() == null) {
            user.setName(usersMap.get(userId).getName());
        }
        if (user.getEmail() == null) {
            user.setEmail(usersMap.get(userId).getEmail());
        }
        usersMap.put(userId, user);
        emails.put(userId, user.getEmail());
        return usersMap.get(userId);
    }

    @Override
    public boolean deleteUser(long userId) {
        User currentUser = usersMap.get(userId);
        if (currentUser == null)
            return false;
        emails.remove(userId);
        usersMap.remove(userId);
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(usersMap.values());
    }

    private int getUniqueUserId() {
        return idCounter++;
    }

    private static boolean validate(String emailStr) {
        Matcher matcher = CommonConstants.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
}
