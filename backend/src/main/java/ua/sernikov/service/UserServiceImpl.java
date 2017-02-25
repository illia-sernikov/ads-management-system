package ua.sernikov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.sernikov.domain.NewUserRequest;
import ua.sernikov.domain.User;
import ua.sernikov.domain.UserRole;
import ua.sernikov.exception.UserAlreadyExistException;
import ua.sernikov.exception.UserNotFoundException;
import ua.sernikov.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private String uuidRegex = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}";

    private UserRepository userRepository;
    private AccountService accountService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_OPERATOR"})
    public User createUser(String name, String email, UserRole role) {
        Assert.hasText(email, "Email should be specified");

        if (isUserExists(email)) {
            throw new UserAlreadyExistException("User with email " + email + " is already exist");
        }

        User user = new User(name, email, role);
        user.setKey(UUID.randomUUID().toString());

        accountService.signUp(email, role);

        return userRepository.save(user);
    }

    @Override
    public User createUser(NewUserRequest userRequest) {
        Assert.notNull(userRequest);

        String userName = userRequest.getName();
        String userEmail = userRequest.getEmail();
        UserRole userRole = userRequest.getRole();

        return createUser(userName, userEmail, userRole);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                             .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsersByRole(UserRole userRole) {
        return userRepository.findAllByRole(userRole).stream()
                             .collect(Collectors.toList());
    }

    @Override
    public User getUserByKey(String userKey) {
        validateKey(userKey);
        return userRepository.findByKey(userKey)
                             .orElse(null);
    }

    @Override
    @Transactional
    public Long deleteUserByKey(String userKey) {
        validateKey(userKey);
        return userRepository.deleteByKey(userKey);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        Assert.notNull(user);

        if (!isUserExists(user.getEmail())) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " not found");
        }

        userRepository.updateByKey(user.getName(), user.getEmail(), user.getKey());

        return user;
    }

    private boolean isUserExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private void validateKey(String key) {
        Assert.hasText(key, "'userKey' should be specified");
        Assert.isTrue(key.matches(uuidRegex), "'userKey' should be a UUID key");
    }
}
