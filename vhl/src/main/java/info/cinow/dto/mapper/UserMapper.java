package info.cinow.dto.mapper;

import info.cinow.dto.UserDto;
import info.cinow.model.User;

/**
 * UserMapper
 */
public interface UserMapper {
    public UserDto toDto(User user);

    public User toUser(UserDto dto);
}