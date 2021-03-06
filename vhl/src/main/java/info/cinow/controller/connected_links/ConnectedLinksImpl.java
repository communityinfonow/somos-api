package info.cinow.controller.connected_links;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * ConnectedLinksImpl
 */
@Component
public class ConnectedLinksImpl implements ConnectedLinks {

    @Override
    public Link configureRelation(WebMvcLinkBuilder linkBuilder, Boolean self, String defaultRelationName) {
        return self ? linkBuilder.withSelfRel() : linkBuilder.withRel(defaultRelationName);
    }

}