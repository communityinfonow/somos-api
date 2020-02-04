package info.cinow.controller.connected_links;

import org.springframework.hateoas.Link;
import org.springframework.util.ReflectionUtils;

import info.cinow.controller.CensusTractPhotoController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * CensusTractPhotoLinks
 */
public class CensusTractPhotoLinks {

        private ConnectedLinks connectedLinks;

        public CensusTractPhotoLinks() {
                this.connectedLinks = new ConnectedLinks();
        }

        public Link photoFile(String censusTractId, String fileName, Boolean self) {
                return this.connectedLinks.configureRelation(linkTo(
                                ReflectionUtils.findMethod(CensusTractPhotoController.class,
                                                "getPhotoFileByNameForTract", Integer.class, String.class),
                                censusTractId, fileName), self, "photo-file");
        }

        public Link croppedPhotoFile(String censusTractId, String fileName, Boolean self) {
                return this.connectedLinks.configureRelation(linkTo(
                                ReflectionUtils.findMethod(CensusTractPhotoController.class,
                                                "getPhotoFileByNameForTract", Integer.class, String.class),
                                censusTractId, fileName), self, "cropped-photo-file");
        }

        public Link photo(String censusTractId, Long photoId, Boolean self) {
                return this.connectedLinks
                                .configureRelation(
                                                linkTo(methodOn(CensusTractPhotoController.class)
                                                                .getPhotoByIdForTract(censusTractId, photoId)),
                                                self, "photo");
        }

        public Link photos(String censusTractId, Boolean self) {
                return this.connectedLinks.configureRelation(
                                linkTo(methodOn(CensusTractPhotoController.class).getAllPhotosForTract(censusTractId)),
                                self, "photos");
        }
}