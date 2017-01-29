package ua.sernikov.service;

import ua.sernikov.domain.User;

public interface UserService {

    User createOperator(String name, String email);

    User createPublisher(String name, String email);
}
