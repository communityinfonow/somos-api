package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import info.cinow.controller.CensusTractPhotoController;

/**
 * CensusTractPhotoLinks
 */
@Component
public class CensusTractPhotoLinksImpl implements CensusTractPhotoLinks {

        @Autowired
        private ConnectedLinks connectedLinks;

        public Link photoFile(String censusTractId, String fileName, Boolean self) {
                return this.connectedLinks.configureRelation(linkTo(
                                ReflectionUtils.findMethod(CensusTractPhotoController.class,
                                                "getPhotoFileByNameForTract", String.class, String.class),
                                censusTractId, fileName), self, "photo-file");
        }

        public Link croppedPhotoFile(String censusTractId, String fileName, Boolean self) {
                return this.connectedLinks.configureRelation(linkTo(
                                ReflectionUtils.findMethod(CensusTractPhotoController.class,
                                                "getPhotoFileByNameForTract", String.class, String.class),
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