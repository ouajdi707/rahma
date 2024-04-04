package com.esprit.spring.ftthback.repository;

import com.esprit.spring.ftthback.models.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande,Long> {

    List<Demande> findByUserId(Long userId);
}
