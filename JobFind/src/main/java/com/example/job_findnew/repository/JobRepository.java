package com.example.job_findnew.repository;

import com.example.job_findnew.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

}
