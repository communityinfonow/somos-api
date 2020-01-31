package info.cinow.controller.admin;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/authenticated")
    public List<String> isAuthenticed(HttpServletRequest request, HttpServletResponse response) {
        List<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(authority -> {
                    if (authority.getAuthority().equals("ROLE_ANONYMOUS")) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                    }
                    return authority.getAuthority();
                }).collect(Collectors.toList());

        return roles;
    }
}
