package info.cinow.dto.mapper;

import info.cinow.dto.RoleDto;
import info.cinow.model.Role;

/**
 * RoleMapper
 */
public interface RoleMapper {

    public RoleDto toDto(Role role);

    public Role toRole(RoleDto dto);
}