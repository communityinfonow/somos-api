package info.cinow.dto.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import info.cinow.controller.CensusTractController;
import info.cinow.controller.connected_links.ConnectedLinks;
import info.cinow.controller.connected_links.IndicatorLinks;
import info.cinow.dto.MatchedCensusTractDto;
import info.cinow.model.CensusTract;
import info.cinow.model.Indicator;
import info.cinow.model.MatchingTract;
import info.cinow.model.MatchingTractIndicator;
import info.cinow.repository.IndicatorDao;

/**
 * CensusTractMapperImpl
 */
@Component("matchedCensusTractMapper")
public class MatchedCensusTractMapperImpl implements MatchedCensusTractMapper {

    @Autowired
    private IndicatorDao indicatorDao;

    private ConnectedLinks connectedLinks;

    @Autowired
    private IndicatorLinks indicatorLinks;

    public MatchedCensusTractMapperImpl() {
        this.connectedLinks = new ConnectedLinks();
    }

    @Override
    public MatchedCensusTractDto toDto(MatchingTract censusTract) {
        if (censusTract == null) {
            return null;
        }
        MatchedCensusTractDto dto = new MatchedCensusTractDto();
        CensusTract childTract = censusTract.getChildTract();
        dto.setId(childTract.getGid());
        dto.setRank(censusTract.getRank());
        dto.setLifeExpectancyDifference(censusTract.getLifeExpentancyDifference());
        dto.setSimilarIndicators(this.toEntityModel(censusTract.getSimilarIndicators(),
                censusTract.getParentTract().getGid(), censusTract.getChildTract().getGid()));
        dto.setDissimilarIndicators(this.toEntityModel(censusTract.getDissimilarIndicators(),
                censusTract.getParentTract().getGid(), childTract.getGid()));
        Indicator lifeExpectancy = this.indicatorDao.findLifeExpectancyIndicator();
        dto.setLifeExpectancyIndicator(new EntityModel<>(lifeExpectancy,
                this.indicatorLinks.lifeExpectancy("parent-life-expectancy", censusTract.getParentTract().getGid()),
                this.indicatorLinks.lifeExpectancy("child-life-expectancy", childTract.getGid()))); // TODO move this
                                                                                                    // somewhere better?
        return dto;
    }

    private <T extends MatchingTractIndicator> List<EntityModel<T>> toEntityModel(Set<T> indicators,
            String parentTractId, String childTractId) {

        return indicators.stream().map(indicator -> {
            return new EntityModel<>(indicator,
                    this.connectedLinks.configureRelation(linkTo(methodOn(CensusTractController.class)
                            .getDataByIndicatorGeography(parentTractId, indicator.getIndicator().getId())), false,
                            "parent-data"),
                    this.connectedLinks.configureRelation(linkTo(methodOn(CensusTractController.class)
                            .getDataByIndicatorGeography(childTractId, indicator.getIndicator().getId())), false,
                            "child-data"));
        }).collect(Collectors.toList());

    }
}