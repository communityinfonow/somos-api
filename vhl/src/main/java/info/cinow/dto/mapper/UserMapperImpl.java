package info.cinow.dto.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import info.cinow.authentication.User;
import info.cinow.authentication.UserDto;

/**
 * UserMapper
 */
@Component("userMapper")
public class UserMapperImpl implements UserMapper {

    @Autowired
    RoleMapper roleMapper;

    PasswordEncoder passwordEncoder;

    @Override
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setEmailAddress(user.getEmailAddress());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setId(user.getId());
        if (user.getRoles() != null) {
            dto.setRoles(
                    user.getRoles().stream().map(role -> this.roleMapper.toDto(role)).collect(Collectors.toList()));
        }

        return dto;
    }

    @Override
    public User toUser(UserDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setEmailAddress(dto.getEmailAddress());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        }
        user.setId(dto.getId());
        if (dto.getRoles() != null) {
            user.setRoles(
                    dto.getRoles().stream().map(role -> this.roleMapper.toRole(role)).collect(Collectors.toList()));
        }

        return user;
    }
}