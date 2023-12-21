package com.example.job_findnew.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long id;

    @Column(name = "job_salary")
    private Long salary;
    @Column(name = "job_name")
    private String name;

    @Column(name = "job_level")
    private String level;

    @Column(name = "job_experience")
    private String experience;

    @Column(name = "job_address")
    private String address;
//, cascade = {CascadeType.MERGE}
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "job_company",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    private List<Company> companies;
}
