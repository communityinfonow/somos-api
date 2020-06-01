package info.cinow.dto;

import java.util.Set;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import info.cinow.model.Indicator;
import lombok.Data;

@Data
public class IndicatorTopicDto {
    private long id;

    private String name;

    private CollectionModel<EntityModel<Indicator>> indicators;
}