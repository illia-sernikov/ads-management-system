package ua.sernikov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ua.sernikov.domain.Account;
import ua.sernikov.domain.UserRole;
import ua.sernikov.repository.AccountRepository;

import javax.annotation.PostConstruct;

@Service("AmsUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private AccountRepository repository;

    @Autowired
    public UserDetailsServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    private void init() {
        String adminPass = BCrypt.hashpw("admin", BCrypt.gensalt());
        Account admin = new Account("admin", adminPass, UserRole.ADMIN);
        repository.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = repository.findByEmail(email)
                                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));

        return new User(account.getEmail(), account.getPassword(), AuthorityUtils.createAuthorityList(account.getRole().getAuthority()));
    }

}
