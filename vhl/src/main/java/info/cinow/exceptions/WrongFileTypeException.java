package info.cinow.exceptions;

public class WrongFileTypeException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public WrongFileTypeException() {
        super("File must be an image");
    }

}