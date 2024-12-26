package com.example.Project_BE.Project_BE.detail;

import com.example.Project_BE.Project_BE.model.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AdminDetail implements UserDetails {

    private final Admin admin;

    // Constructor
    public AdminDetail(Admin admin) {
        this.admin = admin;
    }

    // Static factory method to create AdminDetail from Admin
    public static AdminDetail buildAdmin(Admin admin) {
        return new AdminDetail(admin);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return role as GrantedAuthority
        return Collections.singletonList(new SimpleGrantedAuthority(admin.getRole()));
    }

    @Override
    public String getPassword() {
        return admin.getPassword(); // Return admin's password
    }

    @Override
    public String getUsername() {
        return admin.getUsername(); // Return admin's username (email)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Assuming the account doesn't expire
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Assuming the account is never locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Assuming the credentials never expire
    }

    @Override
    public boolean isEnabled() {
        return true; // Assuming the account is always enabled
    }

    // Get the ID of the admin (Long type)
    public Long getId() {
        return admin.getId(); // Assuming Admin has a getId() method that returns a Long
    }

    // Get the role of the admin (String type)
    public String getRole() {
        return admin.getRole(); // Assuming Admin has a getRole() method that returns a String
    }
}
