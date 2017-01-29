package ua.sernikov.service;

import ua.sernikov.domain.User;

import java.util.List;

public interface UserService {

    User createOperator(String name, String email);

    User createPublisher(String name, String email);

    List<User> getAllOperators();

    List<User> getAllPublishers();
}
