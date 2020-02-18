package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import info.cinow.controller.admin.AdminCensusTractPhotoController;
import info.cinow.controller.admin.AdminPhotoController;

/**
 * AdminPhotoLinks
 */
@Component
public class AdminPhotoLinksImpl implements AdminPhotoLinks {

    @Autowired
    private ConnectedLinks connectedLinks;

    public Link photos(Boolean self) {
        return this.connectedLinks.configureRelation(linkTo(methodOn(AdminPhotoController.class).getAllPhotos()), self,
                "photos");
    }

    public Link photo(String tractId, Long photoId, Boolean self) {
        return this.connectedLinks.configureRelation(
                linkTo(methodOn(AdminCensusTractPhotoController.class).getPhotoByIdForTract(tractId, photoId)), self,
                "photo");
    }

}