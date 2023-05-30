package com.pragma.powerup.usermicroservice.domain.auth;

public interface IPrincipalUser {
    Long getIdUser();
    void setIdUser(Long idUser);

    String getToken();
    void setToken(String token);

    String getRole();
    void setRole(String role);

    String getUser();
    void setUser(String user);
}
