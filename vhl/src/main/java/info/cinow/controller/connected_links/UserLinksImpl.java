package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import info.cinow.controller.admin.UserController;

/**
 * UserLinks
 */
@Component
public class UserLinksImpl implements UserLinks {

    @Autowired
    private ConnectedLinks connectedLinks;

    public Link users(Boolean self) {
        return this.connectedLinks.configureRelation(linkTo(methodOn(UserController.class).getUsers()), self, "users");
    }

    public Link user(Long id, Boolean self) {
        return this.connectedLinks.configureRelation(linkTo(methodOn(UserController.class).getUser(id)), self, "user");
    }
}