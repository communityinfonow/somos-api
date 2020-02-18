package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import info.cinow.controller.PhotoController;

/**
 * PhotoLinks
 */
@Component
public class PhotoLinksImpl implements PhotoLinks {

    @Autowired
    private ConnectedLinks connectedLinks;

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