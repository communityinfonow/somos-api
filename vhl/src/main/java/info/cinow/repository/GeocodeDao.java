package info.cinow.repository;

public interface GeocodeDao<T> {

    public T[] findByAddress(String location);

    public T findByLatLng(double lat, double lng);

    public T[] findByZipCode(String zipCode);

}
