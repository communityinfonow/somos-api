package info.cinow.authentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

        User user = new User();
        user.setId(320L);
        user.setEmailAddress("joemoran231@gmail.com");
        user.setRoles(Arrays.asList(adminRole, superUserRole));
        user.setPassword(encoder.encode("test"));

        this.userDao.save(user);

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