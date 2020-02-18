package info.cinow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * MapBreak
 */
@Entity
@Data
public class MapBreak {

    // TODO delete all map break work? Doesn't seem needed since the life expectancy
    // data will never change

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "break_value")
    private Double breakValue;

    @Column
    private String color;
}