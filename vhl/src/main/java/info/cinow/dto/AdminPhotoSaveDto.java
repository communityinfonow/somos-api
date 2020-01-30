package info.cinow.dto;

import lombok.Data;

@Data
public class AdminPhotoSaveDto {

    private Long id;

    private String fileName;

    private Boolean approved;

    private String description;

    private String ownerEmail;

    private String ownerFirstName;

    private String ownerLastName;

    private String lastEditedBy;

    private String lastEdited;

}