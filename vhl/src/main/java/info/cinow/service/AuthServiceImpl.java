package info.cinow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.authentication.LoginRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * AuthService
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    private Authentication auth;

    public Authentication authorize(LoginRequest loginRequest) {
        try {
            this.auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername().toLowerCase(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (BadCredentialsException e) {
            log.info("Login attempt unsuccessful for username: " + loginRequest.getUsername());
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "Your username or password do not match existing records");
        } catch (DisabledException | LockedException e) {
            log.info("Login attempt unsuccessful for username:" + loginRequest.getUsername());
            throw new ResponseStatusException(HttpStatus.LOCKED,
                    "Your account has either been disabled or locked. Please contact the administrator");
        }
        return auth;
    }

}