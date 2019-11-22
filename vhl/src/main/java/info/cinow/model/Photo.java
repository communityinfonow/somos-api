package info.cinow.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Value;

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

    @Value("${app.awsServices.bucketName}")
    private String imageRepositoryPath;

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
    @ManyToOne
    @JoinColumn(name = "gid")
    private CensusTract censusTract;

    /**
     * Whether the photo has been approved for use by CI: Now staff.
     */
    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean approved;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    // TODO: possible constraint where a description must be included if updated
    // approved to true. This ensures accessibility standards

    /**
     * Get the original, non-unique file name for this photo
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Gets the unique file path name used in S3 Bucket
     * 
     * @return
     */
    public String getFilePathName() {
        return this.id + "_" + this.fileName;
    }

    public String getCroppedFilePathName() {
        return "CROP" + "_" + this.getFilePathName();
        // TODO: what if file name is too long?
    }

    public String getPath() {
        return imageRepositoryPath + "/" + this.getFileName();
    }

}