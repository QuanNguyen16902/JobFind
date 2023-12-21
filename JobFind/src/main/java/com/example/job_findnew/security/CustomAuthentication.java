//package com.example.job_findnew.security;
//
//
//import com.example.job_findnew.model.User;
//import com.example.job_findnew.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.stereotype.Component;
//
//
//import java.util.ArrayList;
//
//@Component
//public class CustomAuthentication implements AuthenticationProvider {
//
//    private final UserService userService;
//
//    @Autowired
//    public CustomAuthentication( UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication)
//            throws AuthenticationException {
//
//        String name = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        User user = (User) userService.loadUserByUsername(name);
//        if (isValidPassword(user , password)) {
//            return new UsernamePasswordAuthenticationToken(
//                    name, password, new ArrayList<>());
//        }else {
//            throw new BadCredentialsException("username or password invalid");
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//
//    private boolean isValidPassword(User user, String password) {
//        return BCrypt.checkpw(password, user.getPassword());
//    }
//}