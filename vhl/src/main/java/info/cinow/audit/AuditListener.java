package info.cinow.audit;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * AuditListener
 */
public class AuditListener {

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyOperation(Auditable auditable) {
        Audit audit = auditable.getAudit();
        if (audit == null) {
            audit = new Audit();
            auditable.setAudit(audit);
        }
        audit.setLastModified(LocalDateTime.now());
    }
}