package info.cinow.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import info.cinow.dto.mapper.UserMapper;
import info.cinow.exceptions.EmailExistsException;

/**
 * UserService
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Autowired
    UserMapper userMapper;

    @Override
    public User registerAdminUser(User user) throws EmailExistsException {
        if (this.emailExists(user.getEmailAddress())) {
            throw new EmailExistsException(user.getEmailAddress());
        }
        return this.dao.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        this.dao.findAll().forEach(user -> {
            users.add(user);
        });
        return users;
    }

    @Override
    public User updateUserInformation(User user) {
        return this.dao.save(this.toEntity(user));
    }

    private boolean emailExists(String emailAddress) {
        return this.dao.findByEmailAddress(emailAddress).isPresent();
    }

    public Optional<User> getUser(Long id) {
        return this.dao.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        this.dao.deleteById(id);
    }

    private User toEntity(User user) {
        User userCopy = user;
        if (userCopy.getId() != null) {
            User oldUser = this.getUser(user.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, user.getId() + ""));

            userCopy.setEnabled(oldUser.isEnabled());
            userCopy.setPassword(oldUser.getPassword());
            userCopy.setTokenExpired(oldUser.isTokenExpired());
        }
        return userCopy;
    }
}