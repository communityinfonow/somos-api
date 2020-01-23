package info.cinow.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.controller.connected_links.AdminPhotoLinks;
import info.cinow.controller.connected_links.CensusTractLinks;
import info.cinow.controller.connected_links.PhotoLinks;
// import info.cinow.controller.connected_links.UserLinks;
import info.cinow.model.AppLinks;
import lombok.extern.slf4j.Slf4j;

/**
 * AppController
 */
@RestController
@RequestMapping("/")
@Slf4j
public class AppController {

    private CensusTractLinks censusTractLinks;

    private PhotoLinks photoLinks;

    // private UserLinks userLinks;

    private AdminPhotoLinks adminPhotoLinks;

    public AppController() {
        this.censusTractLinks = new CensusTractLinks();
        this.adminPhotoLinks = new AdminPhotoLinks();
        this.photoLinks = new PhotoLinks();
        // this.userLinks = new UserLinks();
    }

    @GetMapping
    public AppLinks getAppLinks() {
        return new AppLinks(censusTractLinks.censusTracts(false), null, photoLinks.photos(false));
    }

    @GetMapping("/admin")
    public AppLinks getAdminAppLinks() {
        return new AppLinks(censusTractLinks.censusTracts(false), null, adminPhotoLinks.photos(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleBadRequest(Exception e) throws IOException {
        log.error("An error occurred", e);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}