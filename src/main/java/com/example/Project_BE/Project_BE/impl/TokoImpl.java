package com.example.Project_BE.Project_BE.impl;

import com.example.Project_BE.Project_BE.DTO.TokoDTO;
import com.example.Project_BE.Project_BE.exception.NotFoundException;
import com.example.Project_BE.Project_BE.model.Admin;
import com.example.Project_BE.Project_BE.model.Toko;
import com.example.Project_BE.Project_BE.repository.AdminRepository;
import com.example.Project_BE.Project_BE.repository.TokoRepository;
import com.example.Project_BE.Project_BE.service.TokoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TokoImpl implements TokoService {

    private final TokoRepository tokoRepository;
    private final AdminRepository adminRepository;
    private final ObjectMapper objectMapper;

    public TokoImpl(TokoRepository tokoRepository, AdminRepository adminRepository, ObjectMapper objectMapper) {
        this.tokoRepository = tokoRepository;
        this.adminRepository = adminRepository;
        this.objectMapper = objectMapper;
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
                .orElseThrow(() -> new NotFoundException("Admin not found"));

        Toko toko = objectMapper.convertValue(tokoDTO, Toko.class);
        toko.setAdmin(admin);

        Toko savedToko = tokoRepository.save(toko);
        return objectMapper.convertValue(savedToko, TokoDTO.class);
    }

    @Override
    public TokoDTO editTokoDTO(Long id, Long idAdmin, String tokoJson, MultipartFile file) throws IOException {
        Toko toko = tokoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Toko tidak ditemukan"));

        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin tidak ditemukan"));

        TokoDTO tokoDTO = objectMapper.readValue(tokoJson, TokoDTO.class);

        toko.setAdmin(admin);
        toko.setNamaToko(tokoDTO.getNamaToko());
        toko.setHargaToko(tokoDTO.getHargaToko());

        if (file != null) {
            String base64Image = new String(file.getBytes());
            toko.setFotoUrl(base64Image);
        }

        Toko updatedToko = tokoRepository.save(toko);
        return objectMapper.convertValue(updatedToko, TokoDTO.class);
    }

    @Override
    public void deleteToko(Long id) throws IOException {
        tokoRepository.deleteById(id);
    }
}
