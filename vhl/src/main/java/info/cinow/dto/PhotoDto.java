package info.cinow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PhotoDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDto {

    private Long id;

    private String description;

    private Integer censusTractId;

    private String fileName;

    private Boolean approved;
}