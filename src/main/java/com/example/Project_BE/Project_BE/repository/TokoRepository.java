package com.example.Project_BE.Project_BE.repository;

import com.example.Project_BE.Project_BE.model.Toko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface TokoRepository extends JpaRepository<Toko, Long> {
    List<Toko> findByAdminId(Long idAdmin);
}