package com.example.job_findnew.controller;

import com.example.job_findnew.model.Role;
import com.example.job_findnew.service.RoleService;
import com.example.job_findnew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @Autowired
    private UserService service;
    public void principal(Model model, Principal principal){

        String un = principal.getName();
        model.addAttribute("user", service.findByUserName(un));
    }

    @GetMapping("/roles")
    public String listRoles(Model model, Principal principal){
        Collection<Role> roles = roleService.listAll();
        model.addAttribute("roles" , roles);
        principal(model, principal);
        return "roles/index";
    }

    @GetMapping("/roles/create")
    public String showAddRole(Model model, Principal principal){
        Role role = new Role();
        model.addAttribute("role", role);
        principal(model, principal);
        return "roles/create";
    }

    @PostMapping("/roles/save")
    public String saveRole(@ModelAttribute("role") Role role){
        roleService.save(role);
        return "redirect:/roles";
    }

    @GetMapping("/roles/edit/{id}")
    public String showEditRole(@PathVariable("id") Long id,Model model, Principal principal ){
        Role role = roleService.get(id);
        model.addAttribute("role", role);
        principal(model,principal);
        return "roles/edit";
    }

    @GetMapping("/roles/delete/{id}")
    public String deleteRole(@PathVariable("id") Long id){
        roleService.delete(id);
        return "redirect:/roles";
    }
}
