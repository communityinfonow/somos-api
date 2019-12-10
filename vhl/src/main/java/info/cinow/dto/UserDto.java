package info.cinow.dto;

import java.util.Collection;

import lombok.Data;

@Data
public class UserDto {

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String password;

    private Long id;

    private Collection<RoleDto> roles;
}
