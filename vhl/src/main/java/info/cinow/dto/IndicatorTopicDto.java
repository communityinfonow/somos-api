package info.cinow.dto;

import java.util.Set;

import info.cinow.model.Indicator;
import lombok.Data;

@Data
public class IndicatorTopicDto {
    private long id;

    private String name;

    private Set<Indicator> indicators;
}