package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;

import info.cinow.controller.admin.AdminPhotoController;

/**
 * AdminPhotoLinks
 */
public class AdminPhotoLinks extends PhotoLinks {

    public Link photos(Boolean self) {
        return this.connectedLinks.configureRelation(linkTo(methodOn(AdminPhotoController.class).getAllPhotos()), self,
                "photos");
    }

}