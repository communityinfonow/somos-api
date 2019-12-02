package info.cinow.dto.mapper;

import info.cinow.authentication.Role;
import info.cinow.authentication.RoleDto;

/**
 * RoleMapper
 */
public interface RoleMapper {

    public RoleDto toDto(Role role);

    public Role toRole(RoleDto dto);
}