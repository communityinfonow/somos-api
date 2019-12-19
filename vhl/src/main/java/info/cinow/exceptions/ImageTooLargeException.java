package info.cinow.exceptions;

public class ImageTooLargeException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ImageTooLargeException(String acceptedSize) {
        super("Image is too large. Please upload images less than " + acceptedSize + " in size.");
    }

}