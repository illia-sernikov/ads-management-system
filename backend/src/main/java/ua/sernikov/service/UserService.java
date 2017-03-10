package ua.sernikov.service;

import ua.sernikov.domain.NewUserRequest;
import ua.sernikov.domain.UpdateUserRequest;
import ua.sernikov.domain.User;
import ua.sernikov.domain.UserRole;

import java.util.List;

public interface UserService {

    User createUser(String name, String email, UserRole userRole);

    User createUser(NewUserRequest userRequest);

    List<User> getAllUsers();

    List<User> getAllUsersByRole(UserRole userRole);

    User getUserByKey(String userKey);

    Long deleteUserByKey(String userKey);

    User updateUser(UpdateUserRequest user);
}
