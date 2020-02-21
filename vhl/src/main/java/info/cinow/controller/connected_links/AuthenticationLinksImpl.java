package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import info.cinow.controller.admin.AuthController;

/**
 * AuthenticationLinks
 */

@Component
public class AuthenticationLinksImpl implements AuthenticationLinks {

    @Autowired
    private ConnectedLinks connectedLinks;

    public Link login() {
        return this.connectedLinks.configureRelation(linkTo(methodOn(AuthController.class).login(null, null)), false,
                "login");
    }
}