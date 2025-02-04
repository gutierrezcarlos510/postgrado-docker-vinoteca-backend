package com.casavieja.platform.rest;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.platform.services.LoginS;
import com.casavieja.platform.services.UtilResponseS;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Login", description = "Gestiona y controla los accesos y la salida del sistema")
@RestController
public class LoginC {
    private final LoginS loginS;
    private final UtilResponseS utilResponseS;

    @Autowired
    public LoginC(LoginS loginS, UtilResponseS utilResponseS) {
        this.loginS = loginS;
        this.utilResponseS = utilResponseS;
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<DataResponse> refreshToken(HttpServletRequest request) {
        try {
            return loginS.refreshtoken(request);
        } catch (Exception e) {
            return utilResponseS.getExceptionController(e);
        }
    }
    @PostMapping("/signout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("successLogout");
    }
}