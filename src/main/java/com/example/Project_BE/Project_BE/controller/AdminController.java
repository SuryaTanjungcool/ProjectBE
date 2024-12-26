package com.example.Project_BE.Project_BE.controller;



import com.example.Project_BE.Project_BE.DTO.PasswordDTO;
import com.example.Project_BE.Project_BE.exception.CommonResponse;
import com.example.Project_BE.Project_BE.exception.ResponseHelper;
import com.example.Project_BE.Project_BE.model.Admin;
import com.example.Project_BE.Project_BE.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;  // Sesuaikan service untuk Admin

    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
        Admin registeredAdmin = adminService.registerAdmin(admin);  // Gunakan registerAdmin untuk admin
        return new ResponseEntity<>(registeredAdmin, HttpStatus.CREATED);
    }

    @GetMapping("/admin/{id}")  // Ubah user menjadi admin
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {  // Ubah user menjadi admin
        Admin admin = adminService.getById(id);  // Ganti userService ke adminService
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @GetMapping("/admins")  // Ubah users menjadi admins
    public ResponseEntity<List<Admin>> getAllAdmins() {  // Ubah users menjadi admins
        List<Admin> admins = adminService.getAll();  // Ganti userService ke adminService
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @PutMapping("/admin/edit/{id}")  // Ubah user menjadi admin
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {  // Ubah user menjadi admin
        Admin updatedAdmin = adminService.edit(id, admin);  // Ganti userService ke adminService
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

    @PutMapping("/admin/edit-password/{id}")  // Ubah user menjadi admin
    public CommonResponse<Admin> updateAdminPassword(@RequestBody PasswordDTO password, @PathVariable Long id) {  // Ubah user menjadi admin
        return ResponseHelper.ok(adminService.putPasswordAdmin(password, id));  // Ganti userService ke adminService
    }

    @DeleteMapping("/admin/delete/{id}")  // Ubah user menjadi admin
    public ResponseEntity<Map<String, Boolean>> deleteAdmin(@PathVariable Long id) {  // Ubah user menjadi admin
        Map<String, Boolean> response = adminService.delete(id);  // Ganti userService ke adminService
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}