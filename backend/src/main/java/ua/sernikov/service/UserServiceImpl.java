package ua.sernikov.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.sernikov.domain.NewUserRequest;
import ua.sernikov.domain.UpdateUserRequest;
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
    private final String randomPassword = BCrypt.hashpw("123456", BCrypt.gensalt());

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public User createUser(String name, String email, UserRole role) {
        Assert.hasText(email, "Email should be specified");

        if (isUserExists(email)) {
            throw new UserAlreadyExistException("User with email " + email + " is already exist");
        }

        User user = new User(name, email, role);
        user.setKey(UUID.randomUUID().toString());
        user.setPassword(randomPassword);

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
    public User updateUser(UpdateUserRequest updateUserRequest) {
        Assert.notNull(updateUserRequest);

        User existingUser = this.getUserByKey(updateUserRequest.getKey());

        if (existingUser == null) {
            throw new UserNotFoundException("User with email " + updateUserRequest.getEmail() + " not found");
        }

        if (StringUtils.isNoneBlank(updateUserRequest.getName())) {
            existingUser.setName(updateUserRequest.getName());
        }

        if (StringUtils.isNoneBlank(updateUserRequest.getEmail())) {
            String email = updateUserRequest.getEmail();
            this.userRepository.findByEmail(email).ifPresent(user -> {
                if (!user.getKey().equals(existingUser.getKey())) {
                    throw new UserAlreadyExistException("User with email " + email + " is already exist");
                }
            });

            existingUser.setEmail(updateUserRequest.getEmail());
        }

        if (StringUtils.isNoneBlank(updateUserRequest.getPassword())) {
            String passwd = BCrypt.hashpw(updateUserRequest.getPassword(), BCrypt.gensalt());
            existingUser.setPassword(passwd);
        }

        return userRepository.save(existingUser);
    }

    private boolean isUserExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private void validateKey(String key) {
        Assert.hasText(key, "'userKey' should be specified");
        Assert.isTrue(key.matches(uuidRegex), "'userKey' should be a UUID key");
    }
}
