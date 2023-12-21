package com.example.job_findnew.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import com.example.job_findnew.model.User;
import com.example.job_findnew.service.UserService;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthSucessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        User user = customUser.getUser();

//        if (user != null) {
//            userService.resetAttempt(user.getUsername());
//        }

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/users");
        } else {
            response.sendRedirect("/home");
        }

    }

}