package info.cinow.audit;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// import info.cinow.authentication.User;
import lombok.Data;

@Embeddable
@Data
/**
 * The fields to collect when auditing.
 */
public class Audit {

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    // @ManyToOne
    // @LastModifiedBy
    // private User lastModifiedBy;
}
