package com.esprit.spring.ftthback.services;

import com.esprit.spring.ftthback.models.Demande;
import com.esprit.spring.ftthback.models.Etat;
import com.esprit.spring.ftthback.models.User;
import com.esprit.spring.ftthback.repository.DemandeRepository;
import com.esprit.spring.ftthback.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeServiceImpl implements DemandeService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DemandeRepository demandeRepository;
    @Override
    public List<Demande> retreiveAllUDemandes() {
return demandeRepository.findAll();
    }

    @Override
    public Demande addDemande(Long userId , Demande demande) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        demande.setUser(user);
        return demandeRepository.save(demande);
    }

    @Override
    public void DeleteDemande(Long id) {
demandeRepository.deleteById(id);

    }

    @Override
    public Demande findById(Long id) {
        return demandeRepository.findById(id).get();
    }

    @Override
    public Demande updateDemande(Demande demande, Long id) {
        demande.setId(id);
        return demandeRepository.save(demande);
    }

    @Override
    public List<Demande> findByUserId(Long userId) {
        return demandeRepository.findByUserId(userId);

    }

    public Demande updateEtatDemande(Etat etatDemande, Long id) {
        Demande demande = demandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée avec l'ID : " + id));
        demande.setEtat(etatDemande);
        return demandeRepository.save(demande);
    }


}
