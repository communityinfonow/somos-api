package info.cinow.exceptions;

public class ImageNameTooLongException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ImageNameTooLongException(int characterLimit) {
        super("Image name is too long. Please upload image with a filename less than " + characterLimit
                + " characters");
    }

}