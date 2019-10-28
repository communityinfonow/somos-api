package info.cinow.repository;

import info.cinow.model.geocodio.GeocodioResponse;
import info.cinow.model.locationiq.LocationIqResult;

public interface GeocodeDao {

    /**
     * Calls
     * 
     * @param locationString
     * @return
     */
    public GeocodioResponse byAddress(String locationString);

    public LocationIqResult[] byPlaceName(String locationString);
}
