package info.cinow.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.controller.connected_links.CensusTractLinks;
import info.cinow.controller.connected_links.CensusTractPhotoLinks;
import info.cinow.dto.CensusTractDto;
import info.cinow.dto.mapper.CensusTractMapper;
import info.cinow.service.CensusTractService;

/**
 * CensusTractController
 */
@RestController(value = "census-tracts")
@RequestMapping("/census-tracts")
public class CensusTractController {

        @Autowired
        CensusTractService censusTractService;

        @Autowired
        private CensusTractMapper censusTractMapper;

        private CensusTractLinks censusTractLinks;

        private CensusTractPhotoLinks censusTractPhotoLinks;

        public CensusTractController() {
                this.censusTractLinks = new CensusTractLinks();
                this.censusTractPhotoLinks = new CensusTractPhotoLinks();
        }

        @GetMapping
        public CollectionModel<EntityModel<CensusTractDto>> getCensusTracts() {
                CollectionModel<EntityModel<CensusTractDto>> tracts = new CollectionModel<>(
                                censusTractService.getAllCensusTracts().stream().map(censusTract -> {
                                        CensusTractDto dto = this.censusTractMapper.toDto(censusTract);
                                        return new EntityModel<>(dto,
                                                        this.censusTractPhotoLinks.photos(dto.getId(), false),
                                                        this.censusTractLinks.censusTracts(true));
                                }).collect(Collectors.toList()));

                return tracts;
        }

        @GetMapping("/latlng/{latlng}")
        public EntityModel<CensusTractDto> getContainingCensusTract(@MatrixVariable Map<String, String> latLng) {
                CensusTractDto dto = this.censusTractMapper.toDto(censusTractService
                                .getCensusTract(Double.parseDouble(latLng.get("lat")),
                                                Double.parseDouble(latLng.get("lng")))
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
                return new EntityModel<>(dto, this.censusTractPhotoLinks.photos(dto.getId(), false));

        }

        @GetMapping("/{id}")
        public EntityModel<CensusTractDto> getCensusTractById(@PathVariable("id") String id) {
                EntityModel<CensusTractDto> tract = new EntityModel<>(
                                this.censusTractMapper.toDto(censusTractService.getCensusTract(id)),
                                this.censusTractLinks.censusTract(id, true),
                                this.censusTractPhotoLinks.photos(id, false));
                return tract;
        }

        @GetMapping("/{id}/matched-tracts")
        public CollectionModel<EntityModel<CensusTractDto>> getMatchedTractsByParentId(@PathVariable("id") String id) {
                CollectionModel<EntityModel<CensusTractDto>> matchedTracts = new CollectionModel<>(
                                censusTractService.getMatchedTracts(id).stream().map(childCensusTract -> {
                                        CensusTractDto dto = this.censusTractMapper.toDto(childCensusTract);
                                        return new EntityModel<>(dto,
                                                        this.censusTractPhotoLinks.photos(childCensusTract.getGid(),
                                                                        false),
                                                        this.censusTractLinks.censusTract(childCensusTract.getGid(),
                                                                        true),
                                                        this.censusTractLinks.matchedTractsByParentId(
                                                                        childCensusTract.getGid()));
                                }).collect(Collectors.toList()));

                return matchedTracts;
        }

}