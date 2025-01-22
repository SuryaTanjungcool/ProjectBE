package com.example.Project_BE.Project_BE.model;

import javax.persistence.*;

@Entity
@Table(name = "toko")
public class Toko {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_toko")
    private String namaToko;



    @Column(name = "harga_toko")
    private Double hargaToko;

    @Column(name = "foto_url")
    private String fotoUrl;

    @ManyToOne
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin admin;

    public Toko() {
    }

    public Toko(Long id, Admin admin, String namaToko, String alamatToko, Double hargaToko, String fotoUrl) {
        this.id = id;
        this.admin = admin;
        this.namaToko = namaToko;

        this.hargaToko = hargaToko;
        this.fotoUrl = fotoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
