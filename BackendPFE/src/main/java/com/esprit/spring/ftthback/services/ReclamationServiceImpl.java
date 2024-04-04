package com.esprit.spring.ftthback.services;

import com.esprit.spring.ftthback.models.Demande;
import com.esprit.spring.ftthback.models.Etat;
import com.esprit.spring.ftthback.models.Reclamation;
import com.esprit.spring.ftthback.repository.ReclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReclamationServiceImpl implements ReclamationService {
    @Autowired
    ReclamationRepository reclamationRepository;
    @Override
    public List<Reclamation> retreiveAllReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation addReclamation(Long userId, Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    @Override
    public void DeleteReclamation(Long id) {
        reclamationRepository.deleteById(id);

    }

    @Override
    public Reclamation findById(Long id) {
        return reclamationRepository.findById(id).get();
    }

    @Override
    public Reclamation updateReclamation(Reclamation reclamation, Long id) {
        reclamation.setId(id);
        return reclamationRepository.save(reclamation);
    }

    @Override
    public List<Reclamation> findByUserId(Long userId) {
        return reclamationRepository.findByUserId(userId);
    }

    @Override
    public Reclamation updateEtatReclamation(Etat etatReclamation, Long id) {
        Reclamation reclamation = reclamationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée avec l'ID : " + id));
        reclamation.setEtat(etatReclamation);
        return reclamationRepository.save(reclamation);
    }
}
