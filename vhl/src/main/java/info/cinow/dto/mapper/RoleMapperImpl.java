package info.cinow.dto.mapper;

import org.springframework.stereotype.Component;

import info.cinow.authentication.Role;
import info.cinow.authentication.RoleDto;

/**
 * RoleMapperImpl
 */
@Component("roleMapper")
public class RoleMapperImpl implements RoleMapper {

    public RoleDto toDto(Role role) {
        if (role == null) {
            return null;
        }

        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }

    public Role toRole(RoleDto dto) {
        if (dto == null) {
            return null;
        }

        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());
        return role;
    }

}