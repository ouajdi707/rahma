package com.esprit.spring.ftthback.repository;

import com.esprit.spring.ftthback.models.Demande;
import com.esprit.spring.ftthback.models.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation,Long> {
    List<Reclamation> findByUserId(Long userId);
}
