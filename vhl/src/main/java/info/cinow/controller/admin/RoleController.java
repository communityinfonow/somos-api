package info.cinow.controller.admin;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.cinow.dto.RoleDto;
import info.cinow.dto.mapper.RoleMapper;
import info.cinow.service.RoleService;

/**
 * RoleController
 */
@RestController
@RequestMapping("/admin/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @GetMapping
    public CollectionModel<EntityModel<RoleDto>> getAllRoles() {
        return new CollectionModel<>(this.roleService.getAllRoles().stream().map(role -> {
            RoleDto dto = this.roleMapper.toDto(role);
            return new EntityModel<>(dto);
        }).collect(Collectors.toList()));
    }

}