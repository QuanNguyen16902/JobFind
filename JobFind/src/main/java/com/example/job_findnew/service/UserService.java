package com.example.job_findnew.service;

import com.example.job_findnew.model.Role;
import com.example.job_findnew.model.User;
import com.example.job_findnew.repository.RoleRepository;
import com.example.job_findnew.repository.UserRepository;
import com.example.job_findnew.security.CustomUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {
//    private static final long lock_duration_time=24*60*60*1000;
//    //private static final long lock_duration_time = 30000;
//
//    public static final long ATTEMPT_TIME = 3;

    private User user;
    @Autowired
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public List<User> listAll(){
        return userRepository.findAll();
    }

    public User save(User user) {
     user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User get(long id) {
        return userRepository.findById(id).get();
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }
    public Page<User> findByUsernameContainingIgnoreCase(String keyword, Pageable paging) {
        return this.userRepository.findByUsernameContainingIgnoreCase(keyword,paging);
    }

    public User findByUserName(String username){
        return this.userRepository.findByUsername(username);
    }
    public User findByPassword(String password){
        return this.userRepository.findByPassword(password);
    }

    public User findByUserNameAndEmail(String username, String email) {
        return userRepository.findByUsernameAndEmail(username, email);
    }


    public UserService(User user) {
        this.user = user;
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//                mapRolesToAuthorities(user.getRoles()));
        return new CustomUser(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public boolean isStatus(){
        return user.isStatus();
    }



//    public void increaseFailedAttempt(User user) {
//        int newFailAttempts = user.getFailedAttempt() + 1;
//        userRepository.updateFailedAttempts(newFailAttempts, user.getUsername());
//    }
//
//    public void resetAttempt(String email) {
//        userRepository.updateFailedAttempts(0, email);
//    }
//
//    public void lock(User user) {
//        user.setAccountNonLocked(false);
//        user.setLockTime(new Date());
//
//        userRepository.save(user);
//    }

//  b
    public boolean unlockAccountTimeExpired(User user) {
//        long lockTimeInMillis = user.getLockTime().getTime();
//        long currentTimeInMillis = System.currentTimeMillis();
//
//        if (lockTimeInMillis + lock_duration_time < currentTimeInMillis) {
//            user.setAccountNonLocked(true);
//            user.setLockTime(null);
//            user.setFailedAttempt(0);
//
//
//            userRepository.save(user);
//
            return true;
        }
//
//        return false;
//    }
}
