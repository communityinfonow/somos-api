package info.cinow.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.cinow.model.Role;
import info.cinow.repository.RoleDao;

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
