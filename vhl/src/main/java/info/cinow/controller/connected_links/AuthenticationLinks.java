package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;

import info.cinow.controller.admin.AuthController;

/**
 * AuthenticationLinks
 */
public class AuthenticationLinks {

    private ConnectedLinks connectedLinks;

    public AuthenticationLinks() {
        this.connectedLinks = new ConnectedLinks();
    }

    public Link login() {
        return this.connectedLinks.configureRelation(linkTo(methodOn(AuthController.class).login(null, null)), false,
                "login");
    }

    public Link logout() {
        return this.connectedLinks.configureRelation(linkTo(methodOn(AuthController.class).logout(null, null)), false,
                "logout");
    }
}