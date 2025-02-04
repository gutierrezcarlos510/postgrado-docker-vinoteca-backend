package com.casavieja.platform.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private JWTService jwtService;
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String header = request.getHeader(JWTServiceImpl.HEADER_STRING);
            if(!requiresAuthentication(header)) {
                chain.doFilter(request, response);
                return;
            }
            UsernamePasswordAuthenticationToken authentication = null;
            if(jwtService.validate(header)) {
                authentication = new UsernamePasswordAuthenticationToken(jwtService.getUsername(header), null, jwtService.getRoles(header));
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ExpiredJwtException ex) {
            log.info("TOKEN REFRESCADO...");
            String isRefreshToken = request.getHeader("isRefreshToken");
            String requestURL = request.getRequestURL().toString();
            // allow for Refresh Token creation if following conditions are true.
            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshToken")) {
                allowForRefreshToken(ex, request);
            } else {
                request.setAttribute("exception", ex);
            }
        } catch (BadCredentialsException ex) {
            request.setAttribute("exception", ex);
        } catch (SignatureException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        chain.doFilter(request, response);
    }
    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        request.setAttribute("claims", ex.getClaims());

    }
    protected boolean requiresAuthentication(String header) {
        if(header == null || !header.startsWith(JWTServiceImpl.TOKEN_PREFIX)) {
            return false;
        }
        return true;
    }
}
