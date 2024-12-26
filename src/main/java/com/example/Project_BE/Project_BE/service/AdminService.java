package com.example.Project_BE.Project_BE.service;

import com.example.Project_BE.Project_BE.DTO.PasswordDTO;
import com.example.Project_BE.Project_BE.model.Admin;  // Ganti User dengan Admin

import java.util.List;
import java.util.Map;

public interface AdminService {

    Admin registerAdmin(Admin admin);  // Ganti User dengan Admin

    Admin getById(Long id);  // Ganti User dengan Admin

    List<Admin> getAll();  // Ganti User dengan Admin

    Admin edit(Long id, Admin admin);  // Ganti User dengan Admin

    Admin putPasswordAdmin(PasswordDTO passwordDTO, Long id);  // Ganti User dengan Admin

    Map<String, Boolean> delete(Long id);  // Ganti User dengan Admin
}