package com.example.job_findnew.controller;

import com.example.job_findnew.FileUploadUtil;
import com.example.job_findnew.model.Company;
import com.example.job_findnew.service.CompanyService;
import com.example.job_findnew.service.UserService;
import jakarta.persistence.SequenceGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService service;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void principal(Model model, Principal principal) {

        String un = principal.getName();
        model.addAttribute("user", service.findByUserName(un));
    }


    @GetMapping("/companies")
    public String listCompanies(Model model, Principal principal) {
        List<Company> companies = companyService.listAll();
        model.addAttribute("companies", companies);
        principal(model, principal);
        return "companies/index";
    }

    @GetMapping("/companies/create")
    public String showAddCompany(Model model, Principal principal) {
        String un = principal.getName();
        Company company = new Company();
        model.addAttribute("company", company);
        model.addAttribute("user", service.findByUserName(un));
        return "companies/create";
    }

    @PostMapping("/companies/save")
    public String saveCompany(@ModelAttribute("company") Company company,
                              @RequestParam("image") MultipartFile file, Model model) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        company.setLogo(filename);
        Company saveCompany = companyService.save(company);
        String uploadDir = "./logo/" + saveCompany.getCompanyId();
        FileUploadUtil.saveFile(uploadDir, filename, file);
        return "redirect:/companies";

    }

    @GetMapping("/companies/edit/{id}")
    public ModelAndView editCompany(@PathVariable(name = "id") Long id, Model model, Principal principal) {
        ModelAndView mav = new ModelAndView("companies/edit");
        Company company = companyService.get(id);
        mav.addObject("company", company);
        principal(model, principal);
        return mav;
    }

    @GetMapping("/companies/delete/{id}")
    public String deleteCompany(@PathVariable(name = "id") Long id, Model model) {
        companyService.delete(id);
        return "redirect:/companies";
    }

}
