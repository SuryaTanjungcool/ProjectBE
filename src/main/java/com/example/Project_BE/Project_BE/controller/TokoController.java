package com.example.Project_BE.Project_BE.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.Project_BE.Project_BE.DTO.TokoDTO;
import com.example.Project_BE.Project_BE.model.Toko;
import com.example.Project_BE.Project_BE.service.TokoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class TokoController {

    private final TokoService tokoService;

    public TokoController(TokoService tokoService) {
        this.tokoService = tokoService;
    }

    @GetMapping("/toko/all")
    public ResponseEntity<List<Toko>> getAllToko() {
        List<Toko> tokoList = tokoService.getAllToko();
        return ResponseEntity.ok(tokoList);
    }

    @GetMapping("/toko/getAllByAdmin/{idAdmin}")
    public ResponseEntity<List<Toko>> getAllByAdmin(@PathVariable Long idAdmin) {
        List<Toko> tokoList = tokoService.getAllByAdmin(idAdmin);
        return ResponseEntity.ok(tokoList);
    }

    @GetMapping("/toko/getById/{id}")
    public ResponseEntity<Toko> getTokoById(@PathVariable Long id) {
        Optional<Toko> toko = tokoService.getTokoById(id);
        return toko.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/toko/tambah/{idAdmin}")
    public ResponseEntity<TokoDTO> tambahToko(
            @PathVariable Long idAdmin,
            @RequestParam("toko") String tokoJson,
            @RequestParam("file") MultipartFile file) throws IOException {

        // Convert the toko JSON string to TokoDTO
        ObjectMapper objectMapper = new ObjectMapper();
        TokoDTO tokoDTO = objectMapper.readValue(tokoJson, TokoDTO.class);

        // Upload the photo and get the photo URL from TokoService
        String fotoUrl = tokoService.uploadFoto(file);  // Call the uploadFoto from the service implementation

        // Set the photo URL in the DTO
        tokoDTO.setFotoUrl(fotoUrl);

        // Save the toko with the photo URL
        TokoDTO savedToko = tokoService.tambahTokoDTO(idAdmin, tokoDTO);

        // Log to ensure the fotoUrl is set correctly
        System.out.println("Saved Toko: " + savedToko);

        return ResponseEntity.ok(savedToko);
    }

    @PutMapping("/toko/editById/{id}")
    public ResponseEntity<TokoDTO> editToko(
            @PathVariable Long id,
            @RequestParam Long idAdmin,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String toko) throws IOException {

        // Deserialize the toko JSON to get shop details
        ObjectMapper objectMapper = new ObjectMapper();
        TokoDTO tokoDTO = objectMapper.readValue(toko, TokoDTO.class);

        // If a new file is provided, upload it and update the fotoUrl
        if (file != null) {
            String fotoUrl = tokoService.editUploadFoto(id, file);
            tokoDTO.setFotoUrl(fotoUrl);
        }

        // Edit the other toko fields without photo
        TokoDTO updatedToko = tokoService.editTokoDTO(id, idAdmin, tokoDTO);
        return ResponseEntity.ok(updatedToko);
    }

    @DeleteMapping("/toko/delete/{id}")
    public ResponseEntity<Void> deleteToko(@PathVariable Long id) throws IOException {
        tokoService.deleteToko(id);
        return ResponseEntity.noContent().build();
    }
}
