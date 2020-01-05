package info.cinow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.cinow.controller.connected_links.CensusTractLinks;
import info.cinow.controller.connected_links.PhotoLinks;
import info.cinow.controller.connected_links.UserLinks;
import info.cinow.model.AppLinks;

/**
 * AppController
 */
@RestController
@RequestMapping("/")
public class AppController {

    private CensusTractLinks censusTractLinks;

    private PhotoLinks photoLinks;

    private UserLinks userLinks;

    public AppController() {
        this.censusTractLinks = new CensusTractLinks();

        this.photoLinks = new PhotoLinks();
        this.userLinks = new UserLinks();
    }

    @GetMapping
    public AppLinks getAppLinks() {
        return new AppLinks(censusTractLinks.censusTracts(false), null, photoLinks.photos(false));
    }

}