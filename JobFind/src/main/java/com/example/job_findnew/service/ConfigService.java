package com.example.job_findnew.service;

import com.example.job_findnew.model.Config;
import com.example.job_findnew.model.Role;
import com.example.job_findnew.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigService {
    @Autowired
    private ConfigRepository configRepository;

    public List<Config> listAll(){
        return this.configRepository.findAll();
    }

    public void save(Config config){
        this.configRepository.save(config);
    }

    public Config getId(long id) {
        return configRepository.findById(id).get();
    }

    public void delete(long id){
        this.configRepository.deleteById(id);
    }

}
