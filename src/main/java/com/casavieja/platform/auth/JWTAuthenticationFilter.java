package com.casavieja.platform.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.casavieja.platform.data.DataLogin;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if(username == null && password == null) {
            DataLogin usuario = null;
            try {
                usuario = new ObjectMapper().readValue(request.getInputStream(), DataLogin.class);
                username = usuario.getUsername();
                password = usuario.getPassword();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        username = username.trim();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        try {
            String token = jwtService.create(authResult);
            response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);
            Map<String, Object> body = new HashMap<>();
            body.put("token", token);
            jwtService.getBodySuccess(body, authResult);

            //recuperamos los datos secundarios del usuario, como ser la sucursal activa,  rol activo
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(200);
            response.setContentType("application/json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> body = new HashMap<>();
        body.put("mensaje", "Error  de autenticacion!!!");
        body.put("error", failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }
}
