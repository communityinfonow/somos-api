package info.cinow.authentication;

import java.util.Date;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import info.cinow.Cookies;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * JwtUtils
 */
@Slf4j
@Component
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String secret;

    @Value("${app.jwtExpirationMs}")
    private int expirationInMs;

    @Value("${app.jwtIssuer}")
    private String issuer;

    public String generateToken(Authentication auth) {
        UserDetails userPrincipal = (UserDetails) auth.getPrincipal();

        return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date()).setIssuer(this.issuer)
                .setExpiration(new Date(new Date().getTime() + this.expirationInMs))
                .signWith(SignatureAlgorithm.HS512, this.secret).compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("An error occurred validting token", e);
        }
        return false;
    }

    public Cookie generateCookie(Authentication auth) {
        Cookie jwtCookie = new Cookie(Cookies.JWT.toString(), this.generateToken(auth));
        jwtCookie.setSecure(true);
        jwtCookie.setHttpOnly(true);
        return jwtCookie;
    }

}
