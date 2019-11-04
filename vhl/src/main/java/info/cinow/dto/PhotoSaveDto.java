package info.cinow.dto;

import lombok.Data;

/**
 * PhotoSaveDto
 */
@Data
public class PhotoSaveDto {

    private String description;
    private Long id;

    private String ownerEmail;

    private String ownerFirstName;

    private String ownerLastName;
    private Long tractId;
}