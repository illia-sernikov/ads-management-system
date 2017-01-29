package ua.sernikov.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.sernikov.domain.User;
import ua.sernikov.domain.UserRole;
import ua.sernikov.exception.UserAlreadyExistException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private Map<String, User> users = new HashMap<>();

    @Override
    public User createOperator(String name, String email) {
        return createUser(name, email, UserRole.OPERATOR);
    }

    @Override
    public User createPublisher(String name, String email) {
        return createUser(name, email, UserRole.PUBLISHER);
    }

    @Override
    public List<User> getAllOperators() {
        return users.values().stream()
                    .filter(user -> user.getRole() == UserRole.OPERATOR)
                    .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllPublishers() {
        return users.values().stream()
                .filter(user -> user.getRole() == UserRole.PUBLISHER)
                .collect(Collectors.toList());
    }

    private User createUser(String name, String email, UserRole role) {
        Assert.hasText(email, "Email should be specified");

        if (users.containsKey(email)) {
            throw new UserAlreadyExistException("User with email " + email + " is already exist");
        }

        User user = new User(name, email, role);
        user.setKey(UUID.randomUUID().toString());

        users.put(email, user);

        return user;
    }
}
