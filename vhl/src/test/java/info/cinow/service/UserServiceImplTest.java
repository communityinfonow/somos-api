package info.cinow.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import info.cinow.dto.mapper.RoleMapper;
import info.cinow.dto.mapper.RoleMapperImpl;
import info.cinow.dto.mapper.UserMapper;
import info.cinow.dto.mapper.UserMapperImpl;
import info.cinow.exceptions.EmailExistsException;
import info.cinow.model.User;
import info.cinow.repository.UserDao;

/**
 * UserServiceImplTest
 */
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @MockBean
    public UserMapper userMapper;

    @MockBean
    private RoleMapper roleMapper;

    @MockBean
    private UserDao userDao;

    @MockBean
    private UserService userService;

    private User user;

    @Before
    public void setup() {
        this.user = new User();
        user.setEmailAddress("emailAddress");
        user.setEnabled(true);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setId(1L);
        user.setPassword("password");
        user.setTokenExpired(false);
        Mockito.when(userDao.findByEmailAddress("emailAddress")).thenReturn(Optional.empty());
        Mockito.when(userDao.save(any(User.class))).thenReturn(user);
        Mockito.when(userDao.findById(anyLong())).thenReturn(Optional.ofNullable(user));

    }

    @Test
    public void saveWorks() throws EmailExistsException {

        User returnUser = this.userService.registerAdminUser(user);
        assertEquals(this.user, returnUser);
    }

    @Test(expected = EmailExistsException.class)
    public void emailExists_ThrowsError() throws EmailExistsException {
        Mockito.when(userDao.findByEmailAddress("emailAddress")).thenReturn(Optional.ofNullable(user));
        this.userService.registerAdminUser(this.user);
    }

    @Test
    public void updateUser_GetsOtherProps() {
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setFirstName("newFirstName");
        newUser.setLastName("newLastName");
        newUser.setEnabled(false);

        user.setFirstName("newFirstName");
        user.setLastName("newLastName");
        user.setEnabled(false);
        User returnUser = this.userService.updateUserInformation(newUser);
        assertEquals(user, returnUser);
    }

}