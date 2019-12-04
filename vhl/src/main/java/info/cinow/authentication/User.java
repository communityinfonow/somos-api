package info.cinow.authentication;

import java.util.Collection;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import info.cinow.audit.Audit;
import info.cinow.audit.AuditListener;
import info.cinow.audit.Auditable;
import lombok.Data;

/**
 * User
 */
@Entity(name = "user_table")
@EntityListeners(AuditListener.class)
@Data
public class User implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Brings last_modified as a column.
     */

    @Embedded
    private Audit audit;

    private String firstName;
    private String lastName;

    private String emailAddress;

    private String password;
    private boolean enabled;
    private boolean tokenExpired;

    @ManyToMany
    @JoinTable(name = "USERS_ROLES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "id"))
    private Collection<Role> roles;

    public String toString() {
        return firstName + " " + lastName;
    }

}