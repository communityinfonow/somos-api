package info.cinow.dto.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import info.cinow.controller.connected_links.IndicatorLinks;
import info.cinow.dto.IndicatorTopicDto;
import info.cinow.model.Indicator;
import info.cinow.model.IndicatorTopic;

@Component("indicatorTopicMapper")
public class IndicatorTopicMapperImpl implements IndicatorTopicMapper {

    @Autowired
    private IndicatorLinks indicatorLinks;

    @Override
    public IndicatorTopicDto toDto(IndicatorTopic topic) {
        if (topic.equals(null)) {
            return null;
        }
        IndicatorTopicDto dto = new IndicatorTopicDto();
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        dto.setIndicators(this.toEntityModel(topic.getIndicators()));
        return dto;
    }

    private CollectionModel<EntityModel<Indicator>> toEntityModel(Set<Indicator> indicators) {
        return new CollectionModel<>(indicators.stream().map(indicator -> {
            return new EntityModel<>(indicator, this.indicatorLinks.allDataByIndicator(indicator.getId(), null, false));
        }).collect(Collectors.toList()));
    }

}