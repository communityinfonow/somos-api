package info.cinow.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * FileSaveErrorHandling
 */
public class FileSaveErrorHandling {

    @Value("${image-file.max-size}")
    private long maxFileSize;

    public void checkFileSize(MultipartFile photo) {
        if (photo.getSize() > maxFileSize * 1048576) {// convert MB to bytes
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                    "Image is too large. Please upload images less than 15MB in size.");
        }

    }

    public void checkContentType(MultipartFile photo) {
        if (!photo.getContentType().contains("image")) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
    }
}