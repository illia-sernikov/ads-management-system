package ua.sernikov.service;

import ua.sernikov.domain.User;

import java.util.List;

public interface OperatorService {

    User createOperator(String name, String email);

    List<User> getAllOperators();

    User getOperatorByKey(String operatorKey);

    User deleteOperatorByKey(String operatorKey);

    User updateOperator(User operator);
}
