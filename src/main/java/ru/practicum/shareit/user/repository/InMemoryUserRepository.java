package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.EmailDuplicateException;
import ru.practicum.shareit.exception.EmailNotValidException;
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
        if (emails.containsValue(user.getUserMail())) {
            throw new EmailDuplicateException(String.format(CommonConstants.EMAIL_DUPLICATE_EXCEPTION_MESSAGE,
                    user.getUserMail()));
        }
        if (user.getUserMail() == null || !validate(user.getUserMail())) {
            throw new EmailNotValidException(String.format(CommonConstants.EMAIL_DUPLICATE_EXCEPTION_MESSAGE,
                    user.getUserMail()));
        }

        user.setUserId(getUniqueUserId());
        usersMap.put(user.getUserId(), user);
        emails.put(user.getUserId(), user.getUserMail());

        return user;
    }

    @Override
    public User readUser(long userId) {
        return usersMap.get(userId);
    }

    @Override
    public User updateUser(long userId, User user) {
        user.setUserId(userId);
        if (emails.containsValue(user.getUserMail()) && !usersMap.get(userId).getUserMail().equals(user.getUserMail())) {
            throw new EmailDuplicateException(String.format(CommonConstants.EMAIL_DUPLICATE_EXCEPTION_MESSAGE,
                    user.getUserMail()));
        }
        if (user.getUserName() == null) {
            user.setUserName(usersMap.get(userId).getUserName());
        }
        if (user.getUserMail() == null) {
            user.setUserMail(usersMap.get(userId).getUserMail());
        }
        usersMap.put(userId, user);
        emails.put(userId, user.getUserMail());
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
