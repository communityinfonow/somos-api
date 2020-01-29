package info.cinow.controller.admin;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.amazonaws.services.kms.model.DisabledException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.authentication.LoginRequest;
import info.cinow.service.AuthService;
import lombok.extern.slf4j.Slf4j;

/**
 * AuthController
 */

@RestController
@RequestMapping("/")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Object login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        return this.authService.authorize(loginRequest).getPrincipal();
    }

    @PostMapping("/logout")
    public String logout(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return null;
        // TODO
    }
}
