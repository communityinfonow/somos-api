package info.cinow.dto.mapper;

import info.cinow.dto.IndicatorTopicDto;
import info.cinow.model.IndicatorTopic;

public interface IndicatorTopicMapper {
    public IndicatorTopicDto toDto(IndicatorTopic topic);
}