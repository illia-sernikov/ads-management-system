package ua.sernikov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.sernikov.domain.User;
import ua.sernikov.repository.UserRepository;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String signIn(User user) {
        String email = user.getEmail();
        validatePassword(email, user.getPassword());

        Optional<User> optional = repository.findByEmail(email);
        String token = "";

        if (optional.isPresent()) {
            User existingUser = optional.get();
            token = new String(Base64.encode(existingUser.toString().getBytes()));
        }

        return token;
    }

    private void validatePassword(String email, String password) {
        repository.findByEmail(email).ifPresent(account -> {
            if (!passwordEncoder.matches(password, account.getPassword())) {
                throw new BadCredentialsException("Wrong password");
            }
        });
    }
}
