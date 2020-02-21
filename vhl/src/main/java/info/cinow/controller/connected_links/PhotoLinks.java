package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;

import info.cinow.controller.PhotoController;

/**
 * PhotoLinks
 */
public interface PhotoLinks {

    public Link photoMetadata(Long id, Boolean self);

    public Link photo(Long id, Boolean self);

    public Link photos(Boolean self);

}