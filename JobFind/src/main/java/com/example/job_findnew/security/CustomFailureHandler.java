package com.example.job_findnew.security;

import java.io.IOException;

import com.example.job_findnew.model.User;
import com.example.job_findnew.repository.UserRepository;
import com.example.job_findnew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        User user = userRepo.findByUsername(username);
        String password = request.getParameter("password");


//        if (user != null) {
//            if (user.isStatus()) {
//
//                if (user.isAccountNonLocked()) {
////                    if (user.getFailedAttempt() < UserService.ATTEMPT_TIME - 1) {
////                        userService.increaseFailedAttempt(user);
////                    } else {
////                        userService.lock(user);
////                        exception = new LockedException("Your account is locked !! failed attempt 3");
////                    }
//                    exception = new LockedException("Account is unlocked! Please try to login");
//
//                } else if (!user.isAccountNonLocked()) {
////                    if (userService.unlockAccountTimeExpired(user)) {
////                        exception = new LockedException("Account is unlocked! Please try to login");
////                    } else {
//                        exception = new LockedException("Account is locked! Please try after active");
//                    }
//                }
//            else {
//                exception = new LockedException("Account is inactive...verify account");
//            }
        if (user == null || password == null) {
            exception = new BadCredentialsException("Invalid Username or password");
        }
        else {
                if (!user.isStatus()) {
                    exception = new LockedException("Account is locked! Please try after active");
                }
        }

        super.setDefaultFailureUrl("/loginForm?error");
        super.onAuthenticationFailure(request, response, exception);
    }

}