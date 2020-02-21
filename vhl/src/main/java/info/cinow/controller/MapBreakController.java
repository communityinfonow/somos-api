package info.cinow.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.cinow.dto.MapBreakDto;
import info.cinow.dto.mapper.MapBreakMapper;
import info.cinow.service.MapBreakService;

/**
 * MapController
 */
@RestController
@RequestMapping("/map-breaks")
public class MapBreakController {

    @Autowired
    private MapBreakService mapBreakService;

    @Autowired
    private MapBreakMapper mapper;

    @GetMapping
    public CollectionModel<MapBreakDto> getMapBreaks() {
        return new CollectionModel<>(this.mapBreakService.getMapBreaks().stream()
                .map(mapBreak -> this.mapper.toDto(mapBreak)).collect(Collectors.toList()));
    }

}