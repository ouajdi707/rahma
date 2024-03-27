package com.esprit.spring.ftthback.services;

import com.esprit.spring.ftthback.models.Role;

import java.util.List;

public interface RoleService {
    Role AddRole(Role role);
    public void DeleteRole(Long id);
    List<Role> RetreiveAllRole ();
    Role updateRole(Role role, Long id);

}
