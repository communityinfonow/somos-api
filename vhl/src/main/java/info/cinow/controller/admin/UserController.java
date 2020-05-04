package info.cinow.controller.admin;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.controller.connected_links.UserLinks;
import info.cinow.dto.UserDto;
import info.cinow.dto.mapper.UserMapper;
import info.cinow.exceptions.EmailExistsException;
import info.cinow.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * UserController
 */
@RestController
@RequestMapping("/admin/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserLinks userLinks;

    @GetMapping
    public CollectionModel<EntityModel<UserDto>> getUsers() {
        return new CollectionModel<>(this.service.getAllUsers().stream().map(user -> {
            UserDto dto = this.userMapper.toDto(user);
            return new EntityModel<>(dto, this.userLinks.user(user.getId(), true));
        }).collect(Collectors.toList()), this.userLinks.users(true));
    }

    @PostMapping
    public EntityModel<UserDto> createUser(@RequestBody UserDto user) {
        try {
            return new EntityModel<>(
                    this.userMapper.toDto(this.service.registerAdminUser(this.userMapper.toUser(user))),
                    this.userLinks.user(user.getId(), true), this.userLinks.users(false));
        } catch (EmailExistsException e) {
            log.error("An error occurred creating the user: " + user.toString(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public EntityModel<UserDto> getUser(@PathVariable("id") Long id) {
        return new EntityModel<>(this.userMapper.toDto(this.service.getUser(id).orElse(null)),
                this.userLinks.user(id, true), this.userLinks.users(false));
    }

    @PutMapping("/{id}")
    public EntityModel<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto user) {
        return new EntityModel<>(
                this.userMapper.toDto(this.service.updateUserInformation(this.userMapper.toUser(user))),
                this.userLinks.user(id, true), this.userLinks.users(false));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        this.service.deleteUser(id);
    }

}