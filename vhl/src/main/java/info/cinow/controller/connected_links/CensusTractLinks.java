package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.util.ReflectionUtils;

import info.cinow.controller.CensusTractController;
import info.cinow.controller.CensusTractPhotoController;

/**
 * CensusTractLinks
 */
public class CensusTractLinks {
    private ConnectedLinks connectedLinks;

    public CensusTractLinks() {
        this.connectedLinks = new ConnectedLinks();
    }

    public Link censusTracts(Boolean self) {
        return this.connectedLinks.configureRelation(linkTo(methodOn(CensusTractController.class).getCensusTracts()),
                self, "census-tracts");
    }

    public Link censusTract(Integer tractId, Boolean self) {
        return this.connectedLinks.configureRelation(
                linkTo(methodOn(CensusTractController.class).getCensusTract(tractId)), self, "census-tract");
    }

    // TODO matched tracts

}