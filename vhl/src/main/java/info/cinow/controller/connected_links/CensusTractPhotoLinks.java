package info.cinow.controller.connected_links;

import org.springframework.hateoas.Link;

/**
 * CensusTractPhotoLinks
 */
public interface CensusTractPhotoLinks {

        public Link photoFile(String censusTractId, String fileName, Boolean self);

        public Link croppedPhotoFile(String censusTractId, String fileName, Boolean self);

        public Link photo(String censusTractId, Long photoId, Boolean self);

        public Link photos(String censusTractId, Boolean self);
}