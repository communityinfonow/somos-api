package info.cinow.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.cinow.dto.CensusTractDto;
import info.cinow.model.CensusTract;
import info.cinow.service.CensusTractService;
import lombok.extern.slf4j.Slf4j;

/**
 * CensusTractController
 */
@Slf4j
@RestController
@RequestMapping("/census-tracts")
public class CensusTractController {

    @Autowired
    CensusTractService censusTractService;

    @GetMapping
    public List<EntityModel<CensusTractDto>> getCensusTracts() {
        List<EntityModel<CensusTractDto>> tracts = censusTractService.getAllCensusTracts().stream()
                .map(censusTract -> new EntityModel<>(censusTract,
                        linkTo(methodOn(CensusTractPhotoController.class).getPhotos(censusTract.getId()))
                                .withRel("photos")))
                .collect(Collectors.toList());

        return tracts;
    }

    @GetMapping("/{id}")
    public EntityModel<CensusTract> getCensusTract(@PathVariable("id") Integer id) {
        return null;
        // TODO
    }

}