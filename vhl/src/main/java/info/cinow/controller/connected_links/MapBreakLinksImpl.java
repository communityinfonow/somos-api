package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import info.cinow.controller.MapBreakController;

/**
 * MapBreakLinks
 */
@Component
public class MapBreakLinksImpl implements MapBreakLinks {

    @Autowired
    private ConnectedLinks connectedLinks;

    public Link allMapBreaks() {
        return this.connectedLinks.configureRelation(linkTo(methodOn(MapBreakController.class).getMapBreaks()), false,
                "map-breaks");
    }
}