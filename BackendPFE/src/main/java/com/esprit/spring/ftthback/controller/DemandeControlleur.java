package com.esprit.spring.ftthback.controller;

import com.esprit.spring.ftthback.models.Demande;
import com.esprit.spring.ftthback.models.Etat;
import com.esprit.spring.ftthback.services.DemandeService;
import com.esprit.spring.ftthback.services.DemandeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demande")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class DemandeControlleur {
    @Autowired
    private DemandeServiceImpl demandeService;
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESPONSABLE')")
    @GetMapping("/get")
    public List<Demande> retreiveAllUDemandes() {
        return demandeService.retreiveAllUDemandes();
    }
    @PostMapping("/add/{userId}")
    public Demande addDemande(@PathVariable Long userId, @RequestBody Demande demande) {
        return demandeService.addDemande(userId, demande);
    }
    @DeleteMapping("/{id}")
    public void deleteDemande(@PathVariable Long id) {
        demandeService.DeleteDemande(id);
    }

    @GetMapping("/{id}")
    public Demande findById(@PathVariable Long id) {
        return demandeService.findById(id);
    }
    @PreAuthorize("hasRole('USER') or hasRole('RESPONSABLE')")
    @PutMapping("/{id}")
    public Demande updateDemande(@RequestBody Demande demande, @PathVariable Long id) {
        return demandeService.updateDemande(demande, id);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESPONSABLE')")
    @PutMapping("/{id}/etat")
    public Demande updateEtatDemande(@RequestBody String etatDemande, @PathVariable Long id) {
        Etat etat = Etat.valueOf(etatDemande);
        return demandeService.updateEtatDemande(etat, id);
    }
    @GetMapping("/get/{userId}")
    public List<Demande> findByUserId(@PathVariable Long userId) {
        return demandeService.findByUserId(userId);
    }
}
