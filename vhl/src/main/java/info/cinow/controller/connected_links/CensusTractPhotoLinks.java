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

        public Link photoFile(Integer tractId, String fileName, Boolean self) {
                return this.connectedLinks
                                .configureRelation(linkTo(
                                                ReflectionUtils.findMethod(CensusTractPhotoController.class,
                                                                "getPhotoFile", Integer.class, String.class),
                                                tractId, fileName), self, "photo-file");
        }

        public Link croppedPhotoFile(Integer tractId, String fileName, Boolean self) {
                return this.connectedLinks.configureRelation(
                                linkTo(ReflectionUtils.findMethod(CensusTractPhotoController.class, "getPhotoFile",
                                                Integer.class, String.class), tractId, fileName),
                                self, "cropped-photo-file");
        }

        public Link photo(Integer tractId, Long photoId, Boolean self) {
                return this.connectedLinks.configureRelation(
                                linkTo(methodOn(CensusTractPhotoController.class).getPhoto(tractId, photoId)), self,
                                "photo");
        }

        public Link photos(Integer tractId, Boolean self) {
                return this.connectedLinks.configureRelation(
                                linkTo(methodOn(CensusTractPhotoController.class).getPhotos(tractId)), self, "photos");
        }
}