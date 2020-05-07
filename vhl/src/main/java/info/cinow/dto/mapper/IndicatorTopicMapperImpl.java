package info.cinow.dto.mapper;

import org.springframework.stereotype.Component;

import info.cinow.dto.IndicatorTopicDto;
import info.cinow.model.IndicatorTopic;

@Component("indicatorTopicMapper")
public class IndicatorTopicMapperImpl implements IndicatorTopicMapper {

    @Override
    public IndicatorTopicDto toDto(IndicatorTopic topic) {
        if (topic.equals(null)) {
            return null;
        }
        IndicatorTopicDto dto = new IndicatorTopicDto();
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        dto.setIndicators(topic.getIndicators());
        return dto;
    }

}