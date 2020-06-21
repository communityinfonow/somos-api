package info.cinow.controller.connected_links;

import org.springframework.hateoas.Link;

/**
 * UserLinks
 */
public interface UserLinks {

    public Link users(Boolean self);

    public Link user(Long id, Boolean self);
}