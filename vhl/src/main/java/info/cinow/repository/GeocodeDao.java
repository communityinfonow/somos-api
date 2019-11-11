package info.cinow.repository;

import info.cinow.model.geocodio.GeocodioResponse;
import info.cinow.model.locationiq.LocationIqResult;

public interface GeocodeDao<T> {

    public T find(String location);
}
