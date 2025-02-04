package com.casavieja.platform.auth;

import com.casavieja.platform.entities.Menu;
import com.casavieja.platform.models.form.UserMain;
import com.casavieja.platform.services.RolS;
import com.casavieja.platform.services.SystemUserS;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Component
public class JWTServiceImpl implements JWTService {
    public static final String SECRET = Base64Utils.encodeToString("altoke7167968".getBytes());
    public static final long EXPIRATION_DATE = 1800000L;//30 minutos 1800000L
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    private final SystemUserS systemUserS;
    private final RolS rolS;

    public JWTServiceImpl(SystemUserS systemUserS, RolS rolS) {
        this.systemUserS = systemUserS;
        this.rolS = rolS;
    }

    @Override
    public void getBodySuccess(Map<String, Object> body, Authentication auth) throws Exception {
        String userName = ((User) auth.getPrincipal()).getUsername();
        DataToken dataUsuario = systemUserS.obtenerParaToken(userName);
//        List<Menu> dataResponse = rolS.listarMenuPorRol(dataUsuario.getRolActivo()).getBody();
        List<Menu> menuList = rolS.listarMenuPorRol(dataUsuario.getRolActivo()).getBody();
        ResponseEntity<UserMain> userMain = systemUserS.obtenerUsuarioParaPrincipal(dataUsuario.getUserId());
        body.put("user", userMain.getBody());
        body.put("menus", menuList);
    }

    @Override
    public String create(Authentication auth) throws Exception {
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
        String userName = ((User) auth.getPrincipal()).getUsername();
        DataToken dataUsuario = systemUserS.obtenerParaToken(userName);
        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
        claims.put("userId", dataUsuario.getUserId());
        claims.put("rolActivo", dataUsuario.getRolActivo());
        claims.put("empresaActiva", dataUsuario.getEmpresaActiva());
        claims.put("sucursalActiva", dataUsuario.getSucursalActiva());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(auth.getName())
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                .compact();
    }

    @Override
    public Integer getSucursalActiva(HttpServletRequest req) {
        Claims claims = getClaims(req);
         return claims.get("sucursalActiva", Integer.class);
    }

    @Override
    public Integer getEmpresaActiva(HttpServletRequest req) {
        Claims claims = getClaims(req);
        return claims.get("empresaActiva", Integer.class);
    }

    @Override
    public Integer getRolActivo(HttpServletRequest req) {
        Claims claims = getClaims(req);
        return claims.get("rolActivo", Integer.class);
    }

    @Override
    public String generateRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();
    }

    @Override
    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {
            throw ex;
        }
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
    }

    @Override
    public Claims getClaims(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
    }



    @Override
    public Long getUserIdSession(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        if (token != null) {
            token.replaceFirst("Bearer ", "");
            return getClaims(token).get("userId", Long.class);
        }
        return null;
    }

    @Override
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaims(token).get("authorities");
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
        return authorities;
    }

    @Override
    public String resolve(String token) {
        if (token != null && !token.isEmpty() && token.startsWith(TOKEN_PREFIX)) {
            return token.replace(TOKEN_PREFIX, "");
        } else {
            return null;
        }
    }
}
