package info.cinow.exceptions;

public class EmailExistsException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public EmailExistsException(String email) {
        super("The email already exists: " + email);
    }

}