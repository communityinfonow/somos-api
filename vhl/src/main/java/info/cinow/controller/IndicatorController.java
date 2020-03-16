package info.cinow.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.cinow.controller.connected_links.IndicatorLinks;
import info.cinow.dto.IndicatorDataDto;
import info.cinow.dto.IndicatorDataSet;
import info.cinow.dto.IndicatorDataSetDto;
import info.cinow.dto.IndicatorDto;
import info.cinow.dto.mapper.IndicatorDataMapper;
import info.cinow.dto.mapper.IndicatorMapper;
import info.cinow.service.IndicatorService;

/**
 * IndicatorController
 */
@RestController
@RequestMapping("/census-tracts")
public class IndicatorController {

    @Autowired
    private IndicatorDataMapper indicatorDataMapper;

    @Autowired
    private IndicatorMapper indicatorMapper;

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private IndicatorLinks indicatorLinks;

    @GetMapping("/{id}/indicators/{indicatorId}/data")
    public EntityModel<IndicatorDataDto> getDataByIndicatorGeography(@PathVariable("id") String censusTractId,
            @PathVariable("indicatorId") Long indicatorId) {
        return new EntityModel<>(this.indicatorDataMapper
                .toDto(this.indicatorService.getDataByIndicatorGeography(censusTractId, indicatorId)));
    }

    @GetMapping("/indicators/{indicatorId}/data")
    public IndicatorDataSetDto getDataByIndicator(@PathVariable("indicatorId") Long indicatorId) {
        IndicatorDataSetDto dto = new IndicatorDataSetDto();
        IndicatorDataSet dataset = indicatorService.getDataByIndicatorId(indicatorId);
        dto.setIndicatorData(dataset.getIndicatorData().stream()
                .map(indicatorData -> this.indicatorDataMapper.toDto(indicatorData)).collect(Collectors.toSet()));
        dto.setMaxValue(dataset.getMaxValue());
        dto.setMinValue(dataset.getMinValue());
        return dto;
    }

    @GetMapping("/indicators")
    public CollectionModel<EntityModel<IndicatorDto>> getAllIndicators() {
        return new CollectionModel<>(this.indicatorService.getAllIndicators().stream().map(indicator -> {
            return new EntityModel<>(this.indicatorMapper.toDto(indicator),
                    this.indicatorLinks.allDataByIndicator(indicator.getId(), null, false));
        }).collect(Collectors.toList()));
    }

}