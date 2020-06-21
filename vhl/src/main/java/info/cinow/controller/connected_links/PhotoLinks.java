package info.cinow.controller.connected_links;

import org.springframework.hateoas.Link;

/**
 * PhotoLinks
 */
public interface PhotoLinks {

    public Link photoMetadata(Long id, Boolean self);

    public Link photo(Long id, Boolean self);

    public Link photos(Boolean self);

}