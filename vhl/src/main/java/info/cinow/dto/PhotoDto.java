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

    private String path;

    private Integer censusTractId;

    private String fileName;

    private Boolean approved;
}