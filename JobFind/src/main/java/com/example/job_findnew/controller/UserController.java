package com.example.job_findnew.controller;

import com.example.job_findnew.FileUploadUtil;
import com.example.job_findnew.model.Role;
import com.example.job_findnew.model.User;
import com.example.job_findnew.repository.UserRepository;
import com.example.job_findnew.service.RoleService;
import com.example.job_findnew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class UserController {
//    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";
    @Autowired
    private UserService service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;

    public UserController(UserService userService) {
        this.service = userService;
    }

    public void principal(Model model, Principal principal){

        String un = principal.getName();
        model.addAttribute("user", service.findByUserName(un));
    }
    @RequestMapping("/home")
    public String home(Model model, Principal principal){
        principal(model, principal);
        return "home-page";
    }

    @GetMapping("/users")
    public String viewHomePage(Model model, @RequestParam(required = false) String keyword,
                               @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size, Principal principal) {
//        return findPaginated(1, model);
        principal(model,principal);
        try {
            List<User> users = new ArrayList<User>();
            Pageable paging = PageRequest.of(page - 1, size);

            Page<User> pageTuts;
            if (keyword == null) {
                pageTuts = userRepository.findAll(paging);
            } else {
                pageTuts = service.findByUsernameContainingIgnoreCase(keyword, paging);
                model.addAttribute("keyword", keyword);
            }

            users = pageTuts.getContent();

            model.addAttribute("users", users);
            model.addAttribute("currentPage", pageTuts.getNumber() + 1);
            model.addAttribute("totalItems", pageTuts.getTotalElements());
            model.addAttribute("totalPages", pageTuts.getTotalPages());
            model.addAttribute("pageSize", size);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "users/index";
    }


    @RequestMapping("/users/create")
    public String showNewUserPage(Model model, Principal principal) {
        User user = new User();
        Collection<Role> roles = roleService.listAll();
        principal(model,principal);
        model.addAttribute("currentUser", user);
        model.addAttribute("roles",roles);

        return "users/create";
    }

    @RequestMapping(value = "/users/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("image") MultipartFile file, Model model) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        user.setAvatar(fileName);
        User user1 = service.save(user);
        String uploadDir = "./avatar/" + user1.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, file);

        return "redirect:/users";
    }


    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("currentUser") User user,
                           @RequestParam("image") MultipartFile file, Model model) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        user.setAvatar(fileName);
        User user1 = service.update(user);
        String uploadDir = "./avatar/" + user1.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, file);

        return "redirect:/users";
    }

    @RequestMapping("/users/edit/{id}")
    public String showEditUserPage(@PathVariable(name = "id") int id, Principal principal, Model model) {
        User user = service.get(id);
        Collection<Role> roles = roleService.listAll();
        model.addAttribute("currentUser", user);
        model.addAttribute("roles", roles);
        principal(model, principal);
        return "users/edit";
    }

    @RequestMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        service.delete(id);
        return "redirect:/users";
    }



    @GetMapping("/users/profile")
    public String showProfile(Model model, Principal principal){
        String un = principal.getName();
        model.addAttribute("user", service.findByUserName(un));
        return "users/profile";
    }
//    @RequestMapping("/users/profile")
//    public ModelAndView showProfile(@PathVariable(name = "id") int id) {
//        ModelAndView mav = new ModelAndView("users/profile");
//        User user = service.get(id);
//        mav.addObject("user", user);
//        return mav;
//    }
}