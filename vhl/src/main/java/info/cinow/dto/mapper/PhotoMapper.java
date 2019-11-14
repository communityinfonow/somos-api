package info.cinow.dto.mapper;

import java.util.Optional;

import info.cinow.model.Photo;

/**
 * PhotoMapper
 */
public interface PhotoMapper<T> {

    public Optional<T> toDto(Photo photo);

    public Optional<Photo> toPhoto(T dto);
}