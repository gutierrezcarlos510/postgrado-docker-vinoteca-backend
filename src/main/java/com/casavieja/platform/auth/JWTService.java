package com.casavieja.platform.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public interface JWTService {
    void getBodySuccess(Map<String, Object> body, Authentication auth) throws Exception;

    String create(Authentication auth) throws JsonProcessingException, Exception;

    abstract Integer getSucursalActiva(HttpServletRequest req);

    Integer getEmpresaActiva(HttpServletRequest req);

    Integer getRolActivo(HttpServletRequest req);

    String generateRefreshToken(Map<String, Object> claims, String subject);

    boolean validate(String token);
    Claims getClaims(String token);

    Claims getClaims(HttpServletRequest req);

    Long getUserIdSession(HttpServletRequest req);

    String getUsername(String token);
    Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;
    String resolve(String token);
}
