package info.cinow.authentication;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.WebUtils;

import info.cinow.Cookies;
import info.cinow.exceptions.AuthenticationException;
import lombok.extern.slf4j.Slf4j;

/**
 * AuthFilter
 */
@Slf4j
@Component(value = "authenticationJwtTokenFilter")
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationManagerBuilder builder;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request).orElseThrow(AuthenticationException::new);

            if (jwtUtils.validateToken(jwt)) {
                String username = this.jwtUtils.getUsernameFromJwtToken(jwt);
                UserDetails userDetails = this.builder.getDefaultUserDetailsService().loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (AuthenticationException e) {
            log.info(e.getMessage());
            // throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * Only apply filter when hitting endpoints starting with /admin
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/admin");

    }

    private Optional<String> parseJwt(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, Cookies.JWT.toString());
        if (cookie == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(cookie.getValue());
    }

}