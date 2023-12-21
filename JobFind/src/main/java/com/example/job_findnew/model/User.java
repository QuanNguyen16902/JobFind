package com.example.job_findnew.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;



    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "email")
    private String email;


    @Lob
    @Column(name = "avatar", length = 64, nullable = true)
    private String avatar;

    @Column(name = "status", columnDefinition = "bit default 0")
    private boolean status;


    @Column(name = "channel", columnDefinition = "bit default 0")
    private boolean channel;

//    @Column(name = "account_non_locked", columnDefinition = "bit default 0")
//    private boolean accountNonLocked;
//
//    @Column(name = "failed_attempt", columnDefinition = "bit default 1")
//    private int failedAttempt;



    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @Transient
    public String getAvatarImgPath(){
        if(avatar == null) return null;
        return "/avatar/" + id + "/" + avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
