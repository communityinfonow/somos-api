package info.cinow.audit;

import info.cinow.audit.Audit;

/**
 * For auditing JPA persistence.
 */
public interface Auditable {
    public Audit getAudit();

    public void setAudit(Audit audit);
}
