package com.example.job_findnew.controller;

import com.example.job_findnew.model.Role;
import com.example.job_findnew.model.User;
import com.example.job_findnew.repository.UserRepository;
import com.example.job_findnew.service.RoleService;
import com.example.job_findnew.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collection;

@Controller
public class LoginController {

    @RequestMapping("/")
    public String directLogin(){
        return "redirect:/home";
    }

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/loginForm")
    public String loginForm(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                return "login";
        }

        return "redirect:/home";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public RedirectView logoutPage (HttpServletRequest request,
                                    HttpServletResponse response) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new RedirectView("/loginForm");
    }


    @RequestMapping("/registerForm")
    public String showRegisterForm(Model model){
        User user = new User();
        Collection<Role> roles = roleService.listAll();
        model.addAttribute("user", user);
        model.addAttribute("roles",roles);
        return "register";
    }

    @PostMapping("/registerForm/save")
    public String registration(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findByUserName(user.getUsername());

        if(existingUser != null ){
            result.rejectValue("username", null, "There is already an account registered with the same username");
        }

        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "registerForm";
        }
        userService.save(user);
        return "redirect:/registerForm?success";
    }


    @GetMapping("/access-denied")
    public String showAccessDenied(){
        return "access-denied";
    }

    // forgot password module
    @GetMapping("/loadForgotPassword")
    public String loadForgotPassword(){
        return "forgot_password";
    }

    @GetMapping("/loadResetPassword/{id}")
    public String loadResetPassword(@PathVariable("id") Long id, Model model){
        model.addAttribute("id", id);
        return "reset_password";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam String username, @RequestParam String email, Model model){
        User user = userService.findByUserNameAndEmail(username, email);
        if(user != null){
            return "redirect:/loadResetPassword/" + user.getId();
        }else {
            return "redirect:/loadForgotPassword?error";
        }
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String psw, @RequestParam Long id, @RequestParam String cpsw){
        User user = userRepository.findById(id).get();
        if(!psw.equals(cpsw)){
            return "redirect:/loadResetPassword/" + id +"?error";
        }
        String encryptPsw = passwordEncoder.encode(psw);
        user.setPassword(encryptPsw);
        User updateUser = userRepository.save(user);
        if(updateUser != null){
            return "redirect:/loadForgotPassword?success";
        }
        return "redirect:/loadForgotPassword?error";
    }
//end reset password

    //start login handler

}
