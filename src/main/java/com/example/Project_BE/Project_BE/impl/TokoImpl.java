package com.example.Project_BE.Project_BE.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.Project_BE.Project_BE.DTO.TokoDTO;
import com.example.Project_BE.Project_BE.exception.NotFoundException;
import com.example.Project_BE.Project_BE.model.Admin;
import com.example.Project_BE.Project_BE.model.Toko;
import com.example.Project_BE.Project_BE.repository.AdminRepository;
import com.example.Project_BE.Project_BE.repository.TokoRepository;
import com.example.Project_BE.Project_BE.service.TokoService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TokoImpl implements TokoService {

    private static final String BASE_URL = "https://s3.lynk2.co/api/s3";
    private final TokoRepository tokoRepository;
    private final AdminRepository adminRepository;
    private final RestTemplate restTemplate = new RestTemplate();

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
                .orElseThrow(() -> new NotFoundException("Admin not found"));

        Toko toko = new Toko();
        toko.setAdmin(admin);
        toko.setNamaToko(tokoDTO.getNamaToko());
        toko.setHargaToko(tokoDTO.getHargaToko());
        toko.setFotoUrl(tokoDTO.getFotoUrl());

        Toko savedToko = tokoRepository.save(toko);

        TokoDTO result = new TokoDTO();
        result.setId(savedToko.getId());
        result.setNamaToko(savedToko.getNamaToko());
        result.setHargaToko(savedToko.getHargaToko());
        result.setFotoUrl(savedToko.getFotoUrl());

        return result;
    }

    @Override
    public TokoDTO editTokoDTO(Long id, Long idAdmin, String tokoJson, MultipartFile file) throws IOException {
        Toko existingToko = tokoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Toko tidak ditemukan"));

        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin tidak ditemukan"));

        ObjectMapper objectMapper = new ObjectMapper();
        TokoDTO tokoDTO = objectMapper.readValue(tokoJson, TokoDTO.class);

        existingToko.setAdmin(admin);
        existingToko.setNamaToko(tokoDTO.getNamaToko());
        existingToko.setHargaToko(tokoDTO.getHargaToko());

        if (file != null) {
            String fotoUrl = uploadFoto(file);
            existingToko.setFotoUrl(fotoUrl);
        }

        Toko updatedToko = tokoRepository.save(existingToko);

        TokoDTO result = new TokoDTO();
        result.setId(updatedToko.getId());
        result.setNamaToko(updatedToko.getNamaToko());
        result.setHargaToko(updatedToko.getHargaToko());
        result.setFotoUrl(updatedToko.getFotoUrl());

        return result;
    }

    @Override
    public void deleteToko(Long id) throws IOException {
        tokoRepository.deleteById(id);
    }

    private String uploadFoto(MultipartFile file) throws IOException {
        String uploadUrl = BASE_URL + "/uploadFoto";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return extractFileUrlFromResponse(response.getBody());
        } else {
            throw new IOException("Failed to upload file: " + response.getStatusCode());
        }
    }

    private String extractFileUrlFromResponse(String responseBody) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(responseBody);
        JsonNode dataNode = jsonResponse.path("data");
        return dataNode.path("url_file").asText();
    }
}
