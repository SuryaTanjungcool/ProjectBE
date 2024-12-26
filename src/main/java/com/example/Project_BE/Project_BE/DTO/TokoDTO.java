package com.example.Project_BE.Project_BE.DTO;

public class TokoDTO {
    private Long id;           // Toko ID
    private Long idAdmin;      // Admin (User) ID
    private String namaMakanan; // Food data only

    // Getters and Setters
    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }
}
