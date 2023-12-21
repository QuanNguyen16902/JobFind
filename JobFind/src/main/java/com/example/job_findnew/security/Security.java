package com.example.job_findnew.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class Security {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                {
//                    authorize .requestMatchers("/**").permitAll();

                    authorize
//                            .requestMatchers("/**").permitAll()
                            .requestMatchers("/coreui/**").permitAll()
                            .requestMatchers("/loginForm").permitAll()
                            .requestMatchers("/registerForm/**").permitAll()


//                            .requestMatchers("/users/**").hasRole("MOD")
//                            .requestMatchers("/users/**").hasRole("ADMIN")


                            .requestMatchers("/users", "/users/save", "/companies", "/companies/save/**").hasAnyRole("ADMIN", "CONTENT","MOD")
                            .requestMatchers("/roles/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
                    ;
                })
                .formLogin(
                        form -> form
                                .loginPage("/loginForm")
                                .loginProcessingUrl("/authenticateTheUser")
                                .failureHandler(failureHandler)
                                .successHandler(sucessHandler)
                                .usernameParameter("username")
                                .passwordParameter("password")
//                                .defaultSuccessUrl("/home")
                                .permitAll()

                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutUrl("/logout")
//                                .logoutSuccessUrl("/loginForm?logout")
                                .permitAll())
                .exceptionHandling(confirgurer ->
                        confirgurer.accessDeniedPage("/access-denied")
                );

        return http.build();


    }
    @Autowired
    public CustomAuthSucessHandler sucessHandler;
    @Autowired
    public CustomFailureHandler failureHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }



}
