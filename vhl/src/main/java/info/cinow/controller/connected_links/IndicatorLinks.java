package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import info.cinow.controller.CensusTractController;
import info.cinow.service.IndicatorService;

/**
 * AuthenticationLinks
 */
@Component
public class IndicatorLinks {

    private ConnectedLinks connectedLinks;

    @Autowired
    IndicatorService service;

    public IndicatorLinks() {
        this.connectedLinks = new ConnectedLinks();
    }

    public Link lifeExpectancy(String label, String censusTractId) {
        return this.connectedLinks.configureRelation(linkTo(methodOn(CensusTractController.class)
                .getDataByIndicatorGeography(censusTractId, this.service.getLifeExpectancyIndicator().getId())), false,
                label);
    }

}