package info.cinow.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import info.cinow.exceptions.ImageNameTooLongException;
import info.cinow.exceptions.ImageTooLargeException;
import info.cinow.exceptions.WrongFileTypeException;

/**
 * FileSaveErrorHandling
 */
@Component
public class FileSaveErrorHandling {

    @Value("${image.max-size}")
    private long maxFileSize;

    private final int S3_FILE_CHARACTER_LIMIT = 1000;

    public void checkFileSize(MultipartFile photo) throws ImageTooLargeException {
        if (photo.getSize() > maxFileSize * 1048576) {// convert MB to bytes
            throw new ImageTooLargeException(maxFileSize + "MB");
        }

    }

    public void checkContentType(MultipartFile photo) throws WrongFileTypeException {
        if (!photo.getContentType().contains("image")) {
            throw new WrongFileTypeException();
        }
    }

    public void checkFileNameSize(String fileName) throws ImageNameTooLongException {
        if (fileName.length() >= this.S3_FILE_CHARACTER_LIMIT) {
            throw new ImageNameTooLongException(this.S3_FILE_CHARACTER_LIMIT);
        }
    }
}