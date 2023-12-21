package com.example.job_findnew.service;

import com.example.job_findnew.model.Job;
import com.example.job_findnew.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public List<Job> listAll(){
        return this.jobRepository.findAll();
    }
    public void save(Job job){
        this.jobRepository.save(job);
    }

    public Job getId(long id){
        return this.jobRepository.findById(id).get();
    }

    public void delete(Long id){
        this.jobRepository.deleteById(id);
    }
}
