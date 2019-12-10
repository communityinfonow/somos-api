package info.cinow.service;

import java.util.List;
import java.util.Optional;

import info.cinow.exceptions.EmailExistsException;
import info.cinow.model.User;

public interface UserService {
    public User registerAdminUser(User user) throws EmailExistsException;

    public List<User> getAllUsers();

    public User updateUserInformation(User user);

    public Optional<User> getUser(Long id);

    public void deleteUser(Long id);
}
