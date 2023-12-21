package com.example.job_findnew.controller;

import com.example.job_findnew.model.Config;
import com.example.job_findnew.model.Role;
import com.example.job_findnew.service.ConfigService;
import com.example.job_findnew.service.RoleService;
import com.example.job_findnew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
public class ConfigController {
    @Autowired
    private ConfigService configService;
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }
    @Autowired
    private UserService service;
    public void principal(Model model, Principal principal){

        String un = principal.getName();
        model.addAttribute("user", service.findByUserName(un));
    }

    @GetMapping("/configs")
    public String listConfig(Model model, Principal principal){
        List<Config> configs = configService.listAll();
        model.addAttribute("configs" , configs);
        principal(model, principal);
        return "configs/index";
    }

    @GetMapping("/configs/create")
    public String showAddConfig(Model model, Principal principal){
        Config config = new Config();
        model.addAttribute("config", config);
        principal(model, principal);
        return "configs/create";
    }

    @PostMapping("/configs/save")
    public String saveConfig(@ModelAttribute("config") Config config){
        configService.save(config);
        return "redirect:/configs";
    }

    @GetMapping("/configs/edit/{id}")
    public String showEditConfig(@PathVariable("id") Long id, Model model, Principal principal ){
        Config config = configService.getId(id);
        model.addAttribute("config", config);
        principal(model,principal);
        return "configs/edit";
    }

    @GetMapping("/configs/delete/{id}")
    public String deleteConfig(@PathVariable("id") Long id){
        configService.delete(id);
        return "redirect:/configs";
    }
}
