package com.example.Project_BE.Project_BE.DTO;

public class TokoDTO {
    private Long id;
    private Long idAdmin;
    private String namaToko;
    private String alamatToko;
    private Double hargaToko;
    private String fotoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getAlamatToko() {
        return alamatToko;
    }

    public void setAlamatToko(String alamatToko) {
        this.alamatToko = alamatToko;
    }

    public Double getHargaToko() {
        return hargaToko;
    }

    public void setHargaToko(Double hargaToko) {
        this.hargaToko = hargaToko;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
