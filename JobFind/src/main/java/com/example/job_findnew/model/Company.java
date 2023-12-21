package com.example.job_findnew.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_logo" ,length = 64)
    private String logo;

    @Column(name = "company_email")
    private String email;

    @Column(name = "company_address")
    private String address;

    @Column(name = "company_phone")
    private String phone;

    @Column(name = "company_description")
    private String description;

    @Transient
    public String getLogoImgPath(){
        if(logo == null) return null;
        return "/logo/" + companyId + "/" + logo;
    }
    @ManyToMany(mappedBy = "companies", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Job> jobs;
}
