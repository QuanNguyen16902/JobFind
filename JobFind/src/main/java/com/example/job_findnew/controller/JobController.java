package com.example.job_findnew.controller;


import com.example.job_findnew.model.Company;
import com.example.job_findnew.model.Config;
import com.example.job_findnew.model.Job;
import com.example.job_findnew.model.Role;
import com.example.job_findnew.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private CompanyService companyService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @Autowired
    private UserService service;
    public void principal(Model model, Principal principal){

        String un = principal.getName();
        model.addAttribute("user", service.findByUserName(un));
    }

    @GetMapping("/jobs")
    public String listJob(Model model, Principal principal){
        List<Job> jobs = jobService.listAll();
        model.addAttribute("jobs" , jobs);
        principal(model, principal);
        return "jobs/index";
    }

    @GetMapping("/jobs/create")
    public String showAddJob(Model model, Principal principal){
        Job job = new Job();
        List<Config> configs = configService.listAll();
        List<Config> newConfigs = new ArrayList<Config>();
        for(Config config : configs){
            if(config.getGroup() == 1){
                newConfigs.add(config);
            }
        }
        List<Company> companies = companyService.listAll();
        model.addAttribute("companies", companies);
        model.addAttribute("configs", newConfigs);
        model.addAttribute("job", job);
        principal(model, principal);
        return "jobs/create";
    }

    @PostMapping("/jobs/save")
    public String saveJob(@ModelAttribute("job") Job job){
        jobService.save(job);
        return "redirect:/jobs";
    }

    @GetMapping("/jobs/edit/{id}")
    public String showEditJob(@PathVariable("id") Long id, Model model, Principal principal ){
        Job job = jobService.getId(id);
        String address = job.getAddress();
//       List<Company> companies = companyService.listAll();

//       model.addAttribute("companies", companies);
        List<Config> configs = configService.listAll();
        List<Config> newConfigs = new ArrayList<Config>();
        for(Config config : configs){
            if(config.getGroup() == 1){
                newConfigs.add(config);
            }
        }
        List<Company> companies = companyService.listAll();
        model.addAttribute("companies", companies);
        model.addAttribute("configs", newConfigs);
        model.addAttribute("oldAddress", address);

        model.addAttribute("job", job);
        principal(model,principal);
        return "jobs/edit";
    }

    @GetMapping("/jobs/delete/{id}")
    public String deleteJob(@PathVariable("id") Long id){
        jobService.delete(id);
        return "redirect:/jobs";
    }
}

