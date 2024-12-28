package com.example.Project_BE.Project_BE.controller;

import com.example.Project_BE.Project_BE.DTO.TokoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Project_BE.Project_BE.model.Toko;
import com.example.Project_BE.Project_BE.service.TokoService;
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

    // Endpoint untuk mendapatkan semua toko dalam bentuk TokoDTO
    @GetMapping("/toko/all")
    public ResponseEntity<List<TokoDTO>> getAllToko() {
        List<TokoDTO> tokoList = tokoService.getAllTokoDTO();  // Memanggil service untuk mendapatkan semua toko dalam bentuk DTO
        return ResponseEntity.ok(tokoList);
    }

    // Endpoint untuk mendapatkan semua toko (dalam bentuk Toko) berdasarkan ID Admin
    @GetMapping("/toko/getAllByAdmin/{idAdmin}")
    public ResponseEntity<List<Toko>> getAllByAdmin(@PathVariable Long idAdmin) {
        List<Toko> tokoList = tokoService.getAllByAdmin(idAdmin);
        return ResponseEntity.ok(tokoList);
    }

    // Endpoint untuk mendapatkan toko berdasarkan ID
    @GetMapping("/toko/getById/{id}")
    public ResponseEntity<Toko> getTokoById(@PathVariable Long id) {
        Optional<Toko> toko = tokoService.getTokoById(id);
        return toko.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint untuk menambah toko
    @PostMapping("/toko/tambah/{idAdmin}")
    public ResponseEntity<TokoDTO> tambahToko(
            @PathVariable Long idAdmin,
            @RequestBody TokoDTO tokoDTO) {
        TokoDTO savedTokoDTO = tokoService.tambahTokoDTO(idAdmin, tokoDTO);
        return ResponseEntity.ok(savedTokoDTO);
    }

    // Endpoint untuk mengedit toko
    @PutMapping(value = "/toko/editById/{id}")
    public ResponseEntity<TokoDTO> editToko(
            @PathVariable Long id,
            @RequestParam Long idAdmin,
            @RequestBody TokoDTO tokoDTO) throws IOException {
        TokoDTO updatedToko = tokoService.editTokoDTO(id, idAdmin, tokoDTO);
        return ResponseEntity.ok(updatedToko);
    }

    // Endpoint untuk menghapus toko
    @DeleteMapping("/toko/delete/{id}")
    public ResponseEntity<Void> deleteToko(@PathVariable Long id) throws IOException {
        tokoService.deleteToko(id);
        return ResponseEntity.noContent().build();
    }
}
