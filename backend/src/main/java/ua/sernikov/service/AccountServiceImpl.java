package ua.sernikov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.sernikov.domain.Account;
import ua.sernikov.domain.UserRole;
import ua.sernikov.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final String randomPassword = BCrypt.hashpw("123456", BCrypt.gensalt());

    private AccountRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String signIn(Account account) {
        String email = account.getEmail();
        validatePassword(email, account.getPassword());

        Optional<Account> optional = repository.findByEmail(email);
        String token = "";

        if (optional.isPresent()) {
            Account existingUser = optional.get();
            token = new String(Base64.encode(existingUser.toString().getBytes()));
        }

        return token;
    }

    @Override
    public Account signUp(String email, UserRole role) {
        Assert.notNull(role, "Role of user should be specified");
        Account account = new Account(email, randomPassword, role);
        return repository.save(account);
    }

    private void validatePassword(String email, String password) {
        repository.findByEmail(email).ifPresent(account -> {
            if (!passwordEncoder.matches(password, account.getPassword())) {
                throw new BadCredentialsException("Wrong password");
            }
        });
    }
}
