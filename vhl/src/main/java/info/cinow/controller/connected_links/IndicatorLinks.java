package info.cinow.controller.connected_links;

import org.springframework.hateoas.Link;

/**
 * AuthenticationLinks
 */
public interface IndicatorLinks {

    public Link lifeExpectancy(String label, String censusTractId);

    public Link allIndicators();

    public Link allDataByIndicatorAndGeography(Long indicatorId, String geoid, String label, boolean self);

    public Link allDataByIndicator(Long indicatorId, String label, boolean self);

    public Link allIndicatorTopics();

}