package info.cinow.exceptions;

public class AuthenticationException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AuthenticationException() {
        super("Authentication failed");
    }

}