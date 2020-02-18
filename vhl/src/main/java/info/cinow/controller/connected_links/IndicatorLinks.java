package info.cinow.controller.connected_links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import info.cinow.controller.CensusTractController;
import info.cinow.service.IndicatorService;

/**
 * AuthenticationLinks
 */
public interface IndicatorLinks {

    public Link lifeExpectancy(String label, String censusTractId);

}