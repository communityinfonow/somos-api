package info.cinow.exceptions;

public class NoDescriptionException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NoDescriptionException(String fileName) {
        super("A description must be entered for picture: " + fileName);
    }

}