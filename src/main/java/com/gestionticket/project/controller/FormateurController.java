package com.gestionticket.project.controller;

import com.gestionticket.project.model.Formateur;
import com.gestionticket.project.service.FormateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formateurs")
public class FormateurController {

    @Autowired
    private FormateurService formateurService;

    @GetMapping
    public List<Formateur> getAllFormateurs() {
        return formateurService.getAllFormateurs();
    }

    @GetMapping("/{id}")
    public Formateur getFormateurById(@PathVariable Long id) {
        return formateurService.getFormateurById(id);
    }

    @PostMapping("/register")
    public Formateur registerFormateur(@RequestBody Formateur formateur) {
        return formateurService.saveFormateur(formateur);
    }

    @DeleteMapping("/{id}")
    public void deleteFormateur(@PathVariable Long id) {
        formateurService.deleteFormateur(id);
    }

}

