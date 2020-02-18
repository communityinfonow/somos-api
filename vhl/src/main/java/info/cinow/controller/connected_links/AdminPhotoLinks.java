package info.cinow.controller.connected_links;

import org.springframework.hateoas.Link;

public interface AdminPhotoLinks {

    public Link photos(Boolean self);

    public Link photo(String tractId, Long photoId, Boolean self);

}
