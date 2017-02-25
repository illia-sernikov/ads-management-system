package ua.sernikov.domain;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN, OPERATOR, PUBLISHER;


    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
