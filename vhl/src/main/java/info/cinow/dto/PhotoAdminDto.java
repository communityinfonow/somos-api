package info.cinow.dto;

import lombok.Data;

/**
 * PhotoAdminDto
 */
@Data
public class PhotoAdminDto {

    private Long id;

    private String censusTractId;

    private String description;

    private String fileName;

    private Boolean approved;

    private String ownerEmail;

    private String ownerFirstName;

    private String ownerLastName;

    private String lastEditedBy;

    private String lastEdited;

}