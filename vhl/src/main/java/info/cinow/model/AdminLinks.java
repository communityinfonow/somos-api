package info.cinow.model;

import org.springframework.hateoas.Link;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AppLinks
 */
@Data
@AllArgsConstructor
public class AdminLinks {

    private Link censusTracts;

    private Link users;

    private Link photos;

    private Link login;

}