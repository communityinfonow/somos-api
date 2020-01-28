package info.cinow.service;

import org.springframework.security.core.Authentication;

import info.cinow.authentication.LoginRequest;

/**
 * AuthService
 */
public interface AuthService {
    public Authentication authorize(LoginRequest loginRequest);
}