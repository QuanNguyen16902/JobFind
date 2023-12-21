package com.example.job_findnew.service;

import com.example.job_findnew.model.Role;
import com.example.job_findnew.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Collection<Role> listAll(){
        return this.roleRepository.findAll();
    }
    public void save(Role role){
        this.roleRepository.save(role);
    }

    public Role get(long id) {
        return roleRepository.findById(id).get();
    }
    public void delete(long id) {
        roleRepository.deleteById(id);
    }


    public Page<Role> findPaginated(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.roleRepository.findAll(pageable);
    }

}
