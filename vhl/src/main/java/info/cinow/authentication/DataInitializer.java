package info.cinow.authentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import info.cinow.model.Privilege;
import info.cinow.model.Role;
import info.cinow.model.User;
import info.cinow.repository.PrivilegeDao;
import info.cinow.repository.RoleDao;
import info.cinow.repository.UserDao;

/**
 * DataInitializer
 */
@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    boolean isSetup = false;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PrivilegeDao privilegeDao;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserDao userDao;

    @Value("${app.superuser1.email}")
    private String user1email;

    @Value("${app.superuser2.email}")
    private String user2email;

    @Value("${app.superuser3.email}")
    private String user3email;

    @Value("${app.superuser1.password}")
    private String user1password;

    @Value("${app.superuser2.password}")
    private String user2password;

    @Value("${app.superuser3.password}")
    private String user3password;

    @Override
    // @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (isSetup) {
            return;
        }

        Privilege read = this.createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege write = this.createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege manageUsers = this.createPrivilegeIfNotFound("CREATE_USERS_PRIVILEGE");

        Role adminRole = this.createRoleIfNotFound("ROLE_ADMIN", Arrays.asList(read, write));
        Role superUserRole = this.createRoleIfNotFound("ROLE_SUPER_USER", Arrays.asList(manageUsers));

        if (!userDao.findByEmailAddress(this.user1email).isPresent()) {
            User user = new User();
            user.setEmailAddress(user1email);
            user.setRoles(Arrays.asList(adminRole, superUserRole));
            user.setPassword(encoder.encode(user1password));
            this.userDao.save(user);
        }

        if (!userDao.findByEmailAddress(this.user2email).isPresent()) {
            User user2 = new User();
            user2.setEmailAddress(user2email);
            user2.setRoles(Arrays.asList(adminRole, superUserRole));
            user2.setPassword(encoder.encode(user2password));
            this.userDao.save(user2);
        }

        if (!userDao.findByEmailAddress(this.user3email).isPresent()) {
            User user3 = new User();
            user3.setEmailAddress(user3email);
            user3.setRoles(Arrays.asList(adminRole, superUserRole));
            user3.setPassword(encoder.encode(user3password));
            this.userDao.save(user3);
        }

        isSetup = true;
    }

    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege;
        try {
            privilege = this.privilegeDao.findByName(name).orElseThrow(NoSuchElementException::new);
        } catch (NoSuchElementException e) {
            privilege = this.privilegeDao.save(new Privilege(name));
        }
        return privilege;
    }

    private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role;
        try {
            role = this.roleDao.findByName(name).orElseThrow(NoSuchElementException::new);
        } catch (NoSuchElementException e) {
            role = this.roleDao.save(new Role(name, privileges));
        }
        return role;
    }

}