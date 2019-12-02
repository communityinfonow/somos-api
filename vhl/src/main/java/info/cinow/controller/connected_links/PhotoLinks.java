package info.cinow.controller.connected_links;

import org.springframework.util.ReflectionUtils;

import info.cinow.controller.CensusTractPhotoController;
import info.cinow.controller.PhotoController;
import info.cinow.controller.connected_links.ConnectedLinks;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;

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

    public Link photos(Boolean self) {
        return this.connectedLinks.configureRelation(linkTo(methodOn(PhotoController.class).getPhotos()), self,
                "photos");
    }

}