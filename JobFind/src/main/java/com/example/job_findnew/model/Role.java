package com.example.job_findnew.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "name")
    private String name;



    @Column(name = "slug")
    private String slug;

    public Role() {
    }
    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
//        return "Role{" + "id=" + id + ", name='" + name + '\'' + '}';
        return name;
    }

    @ManyToMany(mappedBy = "roles" , cascade = {CascadeType.MERGE, CascadeType.REMOVE})

    private List<User> User;

}
