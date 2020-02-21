package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;

import info.cinow.controller.CensusTractController;

/**
 * CensusTractLinks
 */
public interface CensusTractLinks {

    public Link censusTracts(Boolean self);

    public Link censusTract(String censusTractId, Boolean self);

    public Link matchedTractsByParentId(String tractId);
}