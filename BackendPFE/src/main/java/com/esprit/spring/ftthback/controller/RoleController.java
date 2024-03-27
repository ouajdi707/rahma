package com.esprit.spring.ftthback.controller;
import com.esprit.spring.ftthback.models.Role;
import com.esprit.spring.ftthback.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @GetMapping("/retrieve-all-role")
    @ResponseBody
    public List<Role> getAllRole() {
        List<Role> list = roleService.RetreiveAllRole();
        return list;
    }
    @DeleteMapping("/remove-role/{role-id}")
    @ResponseBody
    public void removeRole(@PathVariable("role-id") Long id) {
        roleService.DeleteRole(id);
    }
    @PostMapping("/Add-role")
    @ResponseBody
    public Role addRole(@RequestBody Role role){
        return roleService.AddRole(role);
    }
    @PutMapping(value="/modifyrole/{role-id}")
    public Role modify(@PathVariable (name="role-id") Long id, @RequestBody Role role) {

        return roleService.updateRole(role, id);
    }





}

