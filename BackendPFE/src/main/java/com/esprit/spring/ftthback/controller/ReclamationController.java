package com.esprit.spring.ftthback.controller;

import com.esprit.spring.ftthback.models.Demande;
import com.esprit.spring.ftthback.models.Etat;
import com.esprit.spring.ftthback.models.Reclamation;
import com.esprit.spring.ftthback.services.DemandeService;
import com.esprit.spring.ftthback.services.ReclamationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/reclamation")

public class ReclamationController {

    @Autowired
    private ReclamationServiceImpl reclamationService;
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESPONSABLE')")
    @GetMapping("/GetAll")
    public List<Reclamation> retreiveAllReclamation() {
        return reclamationService.retreiveAllReclamations();
    }
    @PostMapping("/add/{userId}")
    public Reclamation addReclamation(@PathVariable Long userId, @RequestBody Reclamation reclamation) {
        return reclamationService.addReclamation(userId, reclamation);
    }
    @DeleteMapping("/{id}")
    public void deleteReclamation(@PathVariable Long id) {
        reclamationService.DeleteReclamation(id);
    }

    @GetMapping("/{id}")
    public Reclamation findById(@PathVariable Long id) {
        return reclamationService.findById(id);
    }
    @PreAuthorize("hasRole('USER') or hasRole('RESPONSABLE')")
    @PutMapping("/{id}")
    public Reclamation updateReclamation(@RequestBody Reclamation reclamation, @PathVariable Long id) {
        return reclamationService.updateReclamation(reclamation, id);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESPONSABLE')")
    @PutMapping("/{id}/etat")
    public Reclamation updateEtatReclamation(@RequestBody String  etatReclamation, @PathVariable Long id) {
        Etat etat = Etat.valueOf(etatReclamation);
        return reclamationService.updateEtatReclamation(etat, id);
    }
    @GetMapping("/Get/{userId}")
    public List<Reclamation> findByUserId(@PathVariable Long userId) {
        return reclamationService.findByUserId(userId);
    }
}


