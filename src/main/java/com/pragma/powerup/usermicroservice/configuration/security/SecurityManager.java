package com.pragma.powerup.usermicroservice.configuration.security;

import com.pragma.powerup.usermicroservice.domain.auth.IPrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityManager extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    private String[] validUrl = {"/swagger-ui/**", "/v3/api-docs/**", "/actuator/health"};
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private final TokenUtilsImpl tokenUtils = new TokenUtilsImpl();
    private final IPrincipalUser tokenHolder = new TokenHolder();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();

        if (shouldNotFilter(path)) {
            filterChain.doFilter(request, response);
        } else {
            try {
                String token = getToken(request);
                if (token != null && validateToken(token) && getRoles(token) != null && tokenUtils.validateRolePaths(getRoles(token).get(0), path)) {
                    setTokenHolder(token);
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");
                }
            } catch (Exception ex) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
            }
        }

    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // return everything after "Bearer "
        }
        return null;
    }

    private Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private List<String> getRoles(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        return claims.get("roles", List.class);
    }

    private String getUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        return claims.get("sub", String.class);
    }
    private Long getIdUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        return claims.get("id", Long.class);
    }

    private void setTokenHolder(String token){
        tokenHolder.setToken(token);
        tokenHolder.setRole(getRoles(token).get(0));
        tokenHolder.setUser(getUser(token));
        tokenHolder.setIdUser(getIdUser(token));
    }

    private Boolean shouldNotFilter(String currentUrl) {
        if (currentUrl == null) {
            return false;
        }
        for (String url : this.validUrl) {
            if (pathMatcher.matchStart(url, currentUrl)) {
                return true;
            }
        }
        return false;
    }
}
