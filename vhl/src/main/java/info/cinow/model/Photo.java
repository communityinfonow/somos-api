package info.cinow.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import info.cinow.audit.Audit;
import info.cinow.audit.AuditListener;
import info.cinow.audit.Auditable;
import lombok.Data;

/**
 * Photo
 */
@Entity
@Data
@EntityListeners(AuditListener.class)
public class Photo implements Auditable {

    /**
     * Brings last_modified as a column.
     */
    @Embedded
    private Audit audit;

    /**
     * Id doubles as the files name.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String fileName;

    /**
     * Description for accessible front-end.
     */
    @Column
    private String description;

    /**
     * The owner who originally uploaded photo.
     */
    @Column
    private String ownerFirstName;

    @Column
    private String ownerLastName;

    @Column
    private String ownerEmail;

    /**
     * The id of the tract to which the photo belongs.
     */
    @Column
    private Integer tractId;

    /**
     * Whether the photo has been approved for use by CI: Now staff.
     */
    @Column(columnDefinition = "boolean default false")
    private Boolean approved;

    /**
     * Whether the photo has been deleted or not by CI: Now staff.
     */
    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    // TODO: possible constraint where a description must be included if updated
    // approved to true. This ensures accessibility standards

    /**
     * Quick convert into string representation of id as the file name.
     * 
     * @return
     */
    public String getFileName() {
        return this.id + "_" + this.fileName;
    }

}