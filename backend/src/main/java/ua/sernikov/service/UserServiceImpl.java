package ua.sernikov.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.sernikov.domain.User;
import ua.sernikov.domain.UserRole;
import ua.sernikov.exception.UserAlreadyExistException;
import ua.sernikov.exception.UserNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
class UserServiceImpl implements UserService {

    private Map<String, User> users = new HashMap<>();
    private String uuidRegex = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}";

    @Override
    public User createUser(String name, String email, UserRole role) {
        Assert.hasText(email, "Email should be specified");

        if (isUserExists(email)) {
            throw new UserAlreadyExistException("User with email " + email + " is already exist");
        }

        User user = new User(name, email, role);
        user.setKey(UUID.randomUUID().toString());

        users.put(user.getKey(), user);

        return user;
    }

    @Override
    public List<User> getAllUsers(UserRole userRole) {
        return users.values().stream()
                    .filter(user -> user.getRole() == userRole)
                    .collect(Collectors.toList());
    }

    @Override
    public User getUserByKey(String userKey) {
        validateKey(userKey);

        return users.values().stream()
                    .filter(user -> user.getKey().equals(userKey))
                    .findFirst()
                    .orElse(null);
    }

    @Override
    public User removeUserByKey(String userKey) {
        validateKey(userKey);
        return users.remove(userKey);
    }

    @Override
    public User updateUser(User user) {
        Assert.notNull(user);

        if (!isUserExists(user.getEmail())) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " not found");
        }

        String userKey = user.getKey();
        return users.replace(userKey, user);
    }

    private boolean isUserExists(String email) {
        return users.values().stream()
                    .anyMatch(user -> Objects.equals(email, user.getEmail()));
    }

    private void validateKey(String key) {
        Assert.hasText(key, "'userKey' should be specified");
        Assert.isTrue(key.matches(uuidRegex), "'userKey' should be a UUID key");
    }
}
