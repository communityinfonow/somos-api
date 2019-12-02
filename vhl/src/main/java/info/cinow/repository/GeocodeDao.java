package info.cinow.repository;

public interface GeocodeDao<T> {

    public T find(String location);
}
