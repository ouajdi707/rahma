package com.esprit.spring.ftthback.services;

import com.esprit.spring.ftthback.models.Demande;
import com.esprit.spring.ftthback.models.Etat;

import java.util.List;

public interface DemandeService {
    List<Demande> retreiveAllUDemandes ();
    Demande addDemande (Long userId,Demande demande);
    public void DeleteDemande(Long id);
    Demande findById (Long id);
    Demande updateDemande(Demande demande,Long id) ;
    List<Demande> findByUserId(Long userId);
    Demande updateEtatDemande(Etat etatDemande, Long id);
}
