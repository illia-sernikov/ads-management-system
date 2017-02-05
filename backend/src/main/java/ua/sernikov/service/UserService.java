package ua.sernikov.service;

import ua.sernikov.domain.User;
import ua.sernikov.domain.UserRole;

import java.util.List;

interface UserService {

    User createUser(String name, String email, UserRole userRole);

    List<User> getAllUsers(UserRole userRole);

    User getUserByKey(String userKey);

    User deleteUserByKey(String userKey);

    User updateUser(User user);
}
