package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;

import info.cinow.controller.UserController;

/**
 * UserLinks
 */
public class UserLinks {
    private ConnectedLinks connectedLinks;

    public UserLinks() {
        this.connectedLinks = new ConnectedLinks();
    }

    public Link users(Boolean self) {
        return this.connectedLinks.configureRelation(linkTo(methodOn(UserController.class).getUsers()), self, "users");
    }

    public Link user(Long id, Boolean self) {
        return this.connectedLinks.configureRelation(linkTo(methodOn(UserController.class).getUser(id)), self, "user");
    }
}