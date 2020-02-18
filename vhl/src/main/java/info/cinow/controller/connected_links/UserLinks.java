package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;

import info.cinow.controller.admin.UserController;

/**
 * UserLinks
 */
public interface UserLinks {

    public Link users(Boolean self);

    public Link user(Long id, Boolean self);
}