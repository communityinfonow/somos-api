package info.cinow.dto;

import lombok.Data;

/**
 * PhotoSaveDto
 */
@Data
public class PhotoSaveDto {

    private Long id;

    private String description;

    private String ownerEmail;

    private String ownerFirstName;

    private String ownerLastName;

    private String lastEditedBy;

    private String lastEdited;

}