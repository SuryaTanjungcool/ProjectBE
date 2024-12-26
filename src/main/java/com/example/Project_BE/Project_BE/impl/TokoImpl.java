package com.example.Project_BE.Project_BE.impl;

import com.example.Project_BE.Project_BE.DTO.TokoDTO;
import com.example.Project_BE.Project_BE.model.Admin;
import com.example.Project_BE.Project_BE.model.Toko;
import com.example.Project_BE.Project_BE.service.TokoService;
import com.example.Project_BE.Project_BE.repository.TokoRepository;
import com.example.Project_BE.Project_BE.exception.NotFoundException;
import com.example.Project_BE.Project_BE.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TokoImpl implements TokoService {

    private final TokoRepository tokoRepository;
    private final AdminRepository adminRepository;

    public TokoImpl(com.example.Project_BE.Project_BE.repository.TokoRepository tokoRepository, AdminRepository adminRepository) {
        this.tokoRepository = tokoRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Toko> getAllToko() {
        return tokoRepository.findAll();
    }

    @Override
    public List<Toko> getAllByAdmin(Long idAdmin) {
        return tokoRepository.findByAdminId(idAdmin);
    }

    @Override
    public Optional<Toko> getTokoById(Long id) {
        return tokoRepository.findById(id);
    }

    @Override
    public TokoDTO tambahTokoDTO(Long idAdmin, TokoDTO tokoDTO) {
        // Find the admin by id
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin with ID " + idAdmin + " not found"));

        // Create and populate the Toko model with food toko only
        Toko toko = new Toko();
        toko.setAdmin(admin);  // Associate the admin
        toko.setNamaMakanan(tokoDTO.getNamaMakanan()); // Set food toko only

        // Save the toko to the repository
        Toko savedData = tokoRepository.save(toko);

        // Prepare the DTO response
        TokoDTO result = new TokoDTO();
        result.setId(savedData.getId());
        result.setIdAdmin(admin.getId());
        result.setNamaMakanan(savedData.getNamaMakanan(tokoDTO.getNamaMakanan()));

        return result;
    }

    @Override
    public TokoDTO editTokoDTO(Long id, Long idAdmin, TokoDTO tokoDTO) throws IOException {
        // Fetch existing Toko toko
        Toko existingData = tokoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Data not found"));

        // Fetch the admin associated with the toko
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin with ID " + idAdmin + " not found"));

        // Update only food toko
        existingData.setAdmin(admin);
        existingData.getNamaMakanan(tokoDTO.getNamaMakanan());

        // Save updated toko
        Toko updatedData = tokoRepository.save(existingData);

        // Prepare and return DTO response
        TokoDTO result = new TokoDTO();
        result.setId(updatedData.getId());
        result.setIdAdmin(admin.getId());
        result.setNamaMakanan(updatedData.getNamaMakanan(tokoDTO.getNamaMakanan()));

        return result;
    }

    @Override
    public void deleteToko(Long id) throws IOException {
        tokoRepository.deleteById(id);
    }
}
