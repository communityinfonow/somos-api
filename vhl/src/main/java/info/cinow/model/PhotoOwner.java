package info.cinow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class PhotoOwner {

    @Column
    String firstName;
    @Column
    String lastName;

    @Id
    String emailAddress;

}
