package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import info.cinow.controller.CensusTractController;

/**
 * CensusTractLinks
 */
@Component
public class CensusTractLinksImpl implements CensusTractLinks {

    @Autowired
    private ConnectedLinks connectedLinks;

    public Link censusTracts(Boolean self) {
        return this.connectedLinks.configureRelation(linkTo(methodOn(CensusTractController.class).getCensusTracts()),
                self, "census-tracts");
    }

    public Link censusTract(String censusTractId, Boolean self) {
        return this.connectedLinks.configureRelation(
                linkTo(methodOn(CensusTractController.class).getCensusTractById(censusTractId)), self, "census-tract");
    }

    public Link matchedTractsByParentId(String tractId) {
        return this.connectedLinks.configureRelation(
                linkTo(methodOn(CensusTractController.class).getMatchedTractsByParentId(tractId)), false,
                "matched-tracts");
    }
}