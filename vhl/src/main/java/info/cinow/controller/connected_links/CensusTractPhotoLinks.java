package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.util.ReflectionUtils;

import info.cinow.controller.CensusTractPhotoController;

/**
 * CensusTractPhotoLinks
 */
public interface CensusTractPhotoLinks {

        public Link photoFile(String censusTractId, String fileName, Boolean self);

        public Link croppedPhotoFile(String censusTractId, String fileName, Boolean self);

        public Link photo(String censusTractId, Long photoId, Boolean self);

        public Link photos(String censusTractId, Boolean self);
}