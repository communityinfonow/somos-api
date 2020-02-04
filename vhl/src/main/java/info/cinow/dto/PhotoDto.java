package info.cinow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PhotoDto
 */
@Data
public class PhotoDto {

    private Long id;

    private String description;

    private String censusTractId;

    private String fileName;

    private Boolean approved;

}