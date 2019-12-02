package info.cinow.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao dao;

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<Role>();
        this.dao.findAll().forEach(roles::add);
        return roles;
    }

}
