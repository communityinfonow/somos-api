package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import info.cinow.controller.CensusTractController;
import info.cinow.controller.IndicatorController;
import info.cinow.service.IndicatorService;

/**
 * AuthenticationLinks
 */
@Component
public class IndicatorLinksImpl implements IndicatorLinks {

    @Autowired
    private ConnectedLinks connectedLinks;

    @Autowired
    private IndicatorService service;

    public Link lifeExpectancy(String label, String censusTractId) {
        return this.connectedLinks.configureRelation(linkTo(methodOn(IndicatorController.class)
                .getDataByIndicatorGeography(censusTractId, this.service.getLifeExpectancyIndicator().getId())), false,
                label);
    }

    @Override
    public Link allDataByIndicator(Long indicatorId, String label, boolean self) {
        return this.connectedLinks.configureRelation(
                linkTo(methodOn(IndicatorController.class).getDataByIndicator(indicatorId)), self,
                StringUtils.isEmpty(label) ? "data-by-indicator" : label);
    }

    @Override
    public Link allIndicators() {
        return this.connectedLinks.configureRelation(linkTo(methodOn(IndicatorController.class).getAllIndicators()),
                false, "indicators");
    }

    @Override
    public Link allDataByIndicatorAndGeography(Long indicatorId, String geoid, String label, boolean self) {
        return this.connectedLinks.configureRelation(
                linkTo(methodOn(IndicatorController.class).getDataByIndicatorGeography(geoid, indicatorId)), self,
                label);
    }

}