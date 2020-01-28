package info.cinow.authentication;

import lombok.Data;

/**
 * LoginRequest
 */
@Data
public class LoginRequest {

    private String password;

    private String username;
}