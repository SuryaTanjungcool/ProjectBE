package com.example.Project_BE.Project_BE.detail;
import com.example.Project_BE.Project_BE.securityNew.JwtTokenUtil;
import com.example.Project_BE.Project_BE.model.Admin;
import com.example.Project_BE.Project_BE.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomAdminDetails implements UserDetailsService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> adminOptional = adminRepository.findByUsername(username);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            return AdminDetail.buildAdmin(admin);
        }

        throw new UsernameNotFoundException("Admin Not Found with username: " + username);
    }
}