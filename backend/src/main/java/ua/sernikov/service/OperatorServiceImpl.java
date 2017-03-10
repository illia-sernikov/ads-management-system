package ua.sernikov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ua.sernikov.domain.UpdateUserRequest;
import ua.sernikov.domain.User;
import ua.sernikov.domain.UserRole;

import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {

    private UserService userService;

    @Autowired
    public OperatorServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public User createOperator(String name, String email) {
        return userService.createUser(name, email, UserRole.OPERATOR);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllOperators() {
        return userService.getAllUsersByRole(UserRole.OPERATOR);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public User getOperatorByKey(String operatorKey) {
        return userService.getUserByKey(operatorKey);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteOperatorByKey(String operatorKey) {
        userService.deleteUserByKey(operatorKey);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public User updateOperator(UpdateUserRequest updateOperatorRequest) {
        return userService.updateUser(updateOperatorRequest);
    }
}
