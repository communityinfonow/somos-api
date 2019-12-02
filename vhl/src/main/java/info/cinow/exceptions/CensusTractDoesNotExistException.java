package info.cinow.exceptions;

public class CensusTractDoesNotExistException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CensusTractDoesNotExistException(String fileName) {
        super("A census tract is not assigned to photo: " + fileName);
    }

}