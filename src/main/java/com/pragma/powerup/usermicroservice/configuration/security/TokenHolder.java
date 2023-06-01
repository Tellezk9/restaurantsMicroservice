package com.pragma.powerup.usermicroservice.configuration.security;

import com.pragma.powerup.usermicroservice.domain.auth.IPrincipalUser;
import org.springframework.stereotype.Component;

@Component
public class TokenHolder implements IPrincipalUser {
    public static Long idUser;
    public static String token;
    public static String role;
    public static String user;

    @Override
    public Long getIdUser() {
        return idUser;
    }

    @Override
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }

    @Override

    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }
}
