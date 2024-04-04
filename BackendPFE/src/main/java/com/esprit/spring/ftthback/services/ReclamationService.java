package com.esprit.spring.ftthback.services;

import com.esprit.spring.ftthback.models.Demande;
import com.esprit.spring.ftthback.models.Etat;
import com.esprit.spring.ftthback.models.Reclamation;

import java.util.List;

public interface ReclamationService {
    List<Reclamation> retreiveAllReclamations ();
    Reclamation addReclamation (Long userId,Reclamation reclamation);
    public void DeleteReclamation(Long id);
    Reclamation findById (Long id);
    Reclamation updateReclamation(Reclamation reclamation,Long id) ;
    List<Reclamation> findByUserId(Long userId);
    Reclamation updateEtatReclamation(Etat etatReclamation, Long id);

}
