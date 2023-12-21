package com.example.job_findnew.repository;

import com.example.job_findnew.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//
    public User findByUsername(String username);
    public User findByPassword(String password);
    public User findByUsernameAndEmail(String username, String email);
    Page<User> findByUsernameContainingIgnoreCase(String keyword, Pageable pageable);

//    @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.username = ?2")
//    @Modifying
//    public void updateFailedAttempts(int failAttempts, String username);

}
