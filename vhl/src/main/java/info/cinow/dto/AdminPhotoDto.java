package info.cinow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * PhotoDto
 */
@Data
@AllArgsConstructor
public class AdminPhotoDto {

    private Long id;

    private String description;

    private String path;
}