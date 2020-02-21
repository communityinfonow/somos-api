package info.cinow.controller.connected_links;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

/**
 * ConnectedLinks
 */
public interface ConnectedLinks {

    public Link configureRelation(WebMvcLinkBuilder linkBuilder, Boolean self, String defaultRelationName);
}