package info.cinow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * PhotoDto
 */
@Data
@AllArgsConstructor
public class PhotoDto {

    private Long id;

    private String description;

    private Integer tractId;

    // TODO: link to image on S3 bucket
}