package com.example.job_findnew.service;

import com.example.job_findnew.model.Company;
import com.example.job_findnew.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> listAll(){
        return companyRepository.findAll();
    }

    public Company save(Company company){
        return companyRepository.save(company);
    }

    public Company get(Long id){
        return companyRepository.findById(id).get();
    }

    public void delete(Long id){
        companyRepository.deleteById(id);
    }


}
