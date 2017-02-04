package ua.sernikov.service;

import ua.sernikov.domain.User;

import java.util.List;

public interface OperatorService {

    User createOperator(String name, String email);

    List<User> getAllOperators();

    User getOperatorByKey(String operatorKey);

    User removeOperatorByKey(String operatorKey);

    User updateOperator(User operator);
}
