package ua.sernikov.service;

import ua.sernikov.domain.Account;
import ua.sernikov.domain.UserRole;

public interface AccountService {

    String signIn(Account account);

    Account signUp(String email, UserRole role);
}
