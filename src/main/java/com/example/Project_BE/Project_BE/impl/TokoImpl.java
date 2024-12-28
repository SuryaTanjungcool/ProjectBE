package com.example.Project_BE.Project_BE.impl;

import com.example.Project_BE.Project_BE.DTO.TokoDTO;
import com.example.Project_BE.Project_BE.model.Admin;
import com.example.Project_BE.Project_BE.model.Toko;
import com.example.Project_BE.Project_BE.service.TokoService;
import com.example.Project_BE.Project_BE.repository.TokoRepository;
import com.example.Project_BE.Project_BE.exception.NotFoundException;
import com.example.Project_BE.Project_BE.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TokoImpl implements TokoService {

    private final TokoRepository tokoRepository;
    private final AdminRepository adminRepository;

    public TokoImpl(TokoRepository tokoRepository, AdminRepository adminRepository) {
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
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin with ID " + idAdmin + " not found"));

        Toko toko = new Toko();
        toko.setAdmin(admin);
        toko.setNamaMakanan(tokoDTO.getNamaMakanan());
        toko.setHarga(tokoDTO.getHarga());

        Toko savedData = tokoRepository.save(toko);

        TokoDTO result = new TokoDTO();
        result.setId(savedData.getId());
        result.setIdAdmin(admin.getId());
        result.setNamaMakanan(savedData.getNamaMakanan());
        result.setHarga(savedData.getHarga());

        return result;
    }

    @Override
    public TokoDTO editTokoDTO(Long id, Long idAdmin, TokoDTO tokoDTO) {
        Toko existingData = tokoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Toko with ID " + id + " not found"));

        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin with ID " + idAdmin + " not found"));

        existingData.setAdmin(admin);
        existingData.setNamaMakanan(tokoDTO.getNamaMakanan());
        existingData.setHarga(tokoDTO.getHarga());

        Toko updatedData = tokoRepository.save(existingData);

        TokoDTO result = new TokoDTO();
        result.setId(updatedData.getId());
        result.setIdAdmin(admin.getId());
        result.setNamaMakanan(updatedData.getNamaMakanan());
        result.setHarga(updatedData.getHarga());

        return result;
    }

    @Override
    public void deleteToko(Long id) {
        tokoRepository.deleteById(id);
    }

    @Override
    public List<TokoDTO> getAllTokoDTO() {
        List<Toko> tokoList = tokoRepository.findAll();
        return tokoList.stream()
                .map(toko -> new TokoDTO(toko))  // Konversi setiap Toko ke TokoDTO
                .collect(Collectors.toList());
    }
}
