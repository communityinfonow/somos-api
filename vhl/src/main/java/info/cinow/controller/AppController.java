package info.cinow.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.controller.connected_links.AdminPhotoLinksImpl;
import info.cinow.controller.connected_links.AuthenticationLinks;
import info.cinow.controller.connected_links.CensusTractLinks;
import info.cinow.controller.connected_links.IndicatorLinks;
import info.cinow.controller.connected_links.MapBreakLinks;
import info.cinow.controller.connected_links.PhotoLinks;
import info.cinow.controller.connected_links.UserLinks;
import info.cinow.model.AdminLinks;
import info.cinow.model.AppLinks;
import info.cinow.utility.Constants;
import lombok.extern.slf4j.Slf4j;

/**
 * AppController
 */
@RestController
@RequestMapping("/")
@Slf4j
public class AppController {

    @Autowired
    private CensusTractLinks censusTractLinks;

    @Autowired
    private PhotoLinks photoLinks;

    @Autowired
    private UserLinks userLinks;

    @Autowired
    private AdminPhotoLinksImpl adminPhotoLinks;

    @Autowired
    private AuthenticationLinks authLinks;

    @Autowired
    private MapBreakLinks mapBreakLinks;

    @Autowired
    private IndicatorLinks indicatorLinks;

    @GetMapping
    public AppLinks getAppLinks() {
        return new AppLinks(censusTractLinks.censusTracts(false), photoLinks.photos(false),
                mapBreakLinks.allMapBreaks());
    }

    @GetMapping("/admin-links")
    public AdminLinks getAdminAppLinks() {
        return new AdminLinks(this.censusTractLinks.censusTracts(false), this.userLinks.users(false),
                this.adminPhotoLinks.photos(false), this.authLinks.login());

    }

    @GetMapping("/advanced-data-links")
    public EntityModel<String> getAdvancedDataLinks() {
        return new EntityModel<>("advanced data links", indicatorLinks.allIndicators(),
                this.censusTractLinks.censusTracts(false),
                indicatorLinks.allDataByIndicator(Constants.lifeExpectancyId, "life-expectancy-data", false),
                indicatorLinks.allIndicatorTopics());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleBadRequest(Exception e) throws IOException {
        log.error("An error occurred", e);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}