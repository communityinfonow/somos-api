package info.cinow.dto.mapper;

import info.cinow.authentication.User;
import info.cinow.authentication.UserDto;

/**
 * UserMapper
 */
public interface UserMapper {
    public UserDto toDto(User user);

    public User toUser(UserDto dto);
}