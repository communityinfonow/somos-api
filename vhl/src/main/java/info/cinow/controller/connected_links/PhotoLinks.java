package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;

import info.cinow.controller.PhotoController;

/**
 * PhotoLinks
 */
public class PhotoLinks {

    ConnectedLinks connectedLinks;

    public PhotoLinks() {
        this.connectedLinks = new ConnectedLinks();
    }

    public Link photoMetadata(Long id, Boolean self) {
        return this.connectedLinks.configureRelation(linkTo(methodOn(PhotoController.class).getPhotoMetadata(id)), self,
                "gps-coordinates");
    }

    public Link photo(Long id, Boolean self) {
        return this.connectedLinks.configureRelation(linkTo(methodOn(PhotoController.class).getPhoto(id)), self,
                "photo");
    }

    public Link photos(Boolean self) {
        return this.connectedLinks.configureRelation(linkTo(PhotoController.class), self, "photos");
    }

}