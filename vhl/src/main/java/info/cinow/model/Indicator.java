package info.cinow.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

    @Column(name = "is_life_expectancy", columnDefinition = "boolean default false")
    private Boolean isLifeExpectancy;

    @OneToOne
    @JoinColumn(name = "value_type_id")
    private ValueType valueType;

}