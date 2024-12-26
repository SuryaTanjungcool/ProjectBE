package com.example.Project_BE.Project_BE.model;

import javax.persistence.*;

@Entity
@Table(name = "toko")
public class Toko {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_makanan")
    private String namaMakanan;

    @ManyToOne
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin admin;

    public Toko(Long id, String namaMakanan, Admin admin) {
        this.id = id;
        this.namaMakanan = namaMakanan;
        this.admin = admin;
    }

    public Toko() {

    }

    // Getter dan Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaMakanan(String namaMakanan) {
        return this.namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}