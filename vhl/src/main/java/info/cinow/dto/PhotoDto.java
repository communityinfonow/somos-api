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

    private Long tractId;
}