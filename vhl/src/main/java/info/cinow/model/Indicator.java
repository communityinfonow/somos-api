package info.cinow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

/**
 * Indicator
 */
@Entity

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

    @Column(name = "list_order")
    private Long order;

    @Column
    private String notation;

    @OneToOne
    @JoinColumn(name = "value_type_id")
    private ValueType valueType;

    @ManyToOne
    @JsonBackReference
    @JoinColumns({ @JoinColumn(name = "topic_id", referencedColumnName = "id") })
    private IndicatorTopic indicatorTopic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataLabel() {
        return dataLabel;
    }

    public void setDataLabel(String dataLabel) {
        this.dataLabel = dataLabel;
    }

    public Boolean getIsLifeExpectancy() {
        return isLifeExpectancy;
    }

    public void setIsLifeExpectancy(Boolean isLifeExpectancy) {
        this.isLifeExpectancy = isLifeExpectancy;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public IndicatorTopic getIndicatorTopic() {
        return indicatorTopic;
    }

    public void setIndicatorTopic(IndicatorTopic indicatorTopic) {
        this.indicatorTopic = indicatorTopic;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

}