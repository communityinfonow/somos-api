package info.cinow.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import info.cinow.dto.IndicatorTopicDto;
import info.cinow.dto.mapper.IndicatorTopicMapper;
import info.cinow.service.IndicatorTopicService;

@RestController
public class IndicatorTopicController {

    @Autowired
    private IndicatorTopicService indicatorTopicService;

    @Autowired
    private IndicatorTopicMapper indicatorTopicMapper;

    @GetMapping("/indicator-topics")
    public CollectionModel<EntityModel<IndicatorTopicDto>> getAllIndicatorTopics() {
        return new CollectionModel<>(this.indicatorTopicService.getAllTopics().stream().map(topic -> {
            return new EntityModel<>(this.indicatorTopicMapper.toDto(topic));
        }).collect(Collectors.toList()));

    }

}