package info.cinow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * Indicator
 */
@Entity
@Data
public class Indicator {

    @Id
    Long id;

    @Column
    String name;

    @Column
    String description;

    @Column(name = "data_label")
    String dataLabel;

}