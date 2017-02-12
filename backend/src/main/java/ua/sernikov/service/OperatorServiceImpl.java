package ua.sernikov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public User createOperator(String name, String email) {
        return userService.createUser(name, email, UserRole.OPERATOR);
    }

    @Override
    public List<User> getAllOperators() {
        return userService.getAllUsersByRole(UserRole.OPERATOR);
    }

    @Override
    public User getOperatorByKey(String operatorKey) {
        return userService.getUserByKey(operatorKey);
    }

    @Override
    public User deleteOperatorByKey(String operatorKey) {
        return userService.deleteUserByKey(operatorKey);
    }

    @Override
    public User updateOperator(User operator) {
        return userService.updateUser(operator);
    }
}
