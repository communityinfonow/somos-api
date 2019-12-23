package info.cinow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.cinow.controller.connected_links.AdminPhotoLinks;
import info.cinow.controller.connected_links.CensusTractLinks;
import info.cinow.controller.connected_links.UserLinks;
import info.cinow.model.AppLinks;

/**
 * AppController
 */
@RestController
@RequestMapping("/")
public class AppController {

    private CensusTractLinks censusTractLinks;

    private AdminPhotoLinks photoLinks;

    private UserLinks userLinks;

    public AppController() {
        this.censusTractLinks = new CensusTractLinks();

        this.photoLinks = new AdminPhotoLinks();
        this.userLinks = new UserLinks();
    }

    @GetMapping
    public AppLinks getAppLinks() {
        return new AppLinks(censusTractLinks.censusTracts(false), null, photoLinks.photos(false));
    }

}