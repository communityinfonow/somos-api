package info.cinow.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<EntityModel<CensusTract>> getCensusTracts() {
        List<EntityModel<CensusTract>> tracts = censusTractService.getAllCensusTracts().stream()
                .map(censusTract -> new EntityModel<>(censusTract)).collect(Collectors.toList());

        return tracts;
    }

    @GetMapping("/boundaries")
    public EntityModel<FeatureCollection> getBoundaries() {
        EntityModel<FeatureCollection> featureCollection = null;
        try {
            featureCollection = new EntityModel<>(censusTractService.getGeometryCollection());
        } catch (Exception e) {
            log.error("an error happened here", e);
        }
        return featureCollection;
    }

    @GetMapping("/{id}")
    public EntityModel<CensusTract> getCensusTract(@PathVariable("id") Long id) {
        return null;
        // TODO
    }

}