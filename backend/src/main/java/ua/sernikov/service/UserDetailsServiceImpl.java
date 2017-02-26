package ua.sernikov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ua.sernikov.domain.User;
import ua.sernikov.domain.UserRole;
import ua.sernikov.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@Service("AmsUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository repository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    private void init() {
        String adminPass = BCrypt.hashpw("admin", BCrypt.gensalt());
        User admin = new User("admin", "admin", adminPass, UserRole.ADMIN);
        admin.setKey(UUID.randomUUID().toString());
        repository.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email)
                              .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRole().getAuthority());
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(),authorities);
    }
}
