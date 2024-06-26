package com.gestionticket.project.controller;

import com.gestionticket.project.model.Apprenant;
import com.gestionticket.project.service.ApprenantService;
import com.gestionticket.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apprenants")
public class ApprenantController {

    @Autowired
    private ApprenantService apprenantService;

    @Autowired
    private UserService userService; // Injecter le UserService pour les opérations communes

    @GetMapping
    public List<Apprenant> getAllApprenants() {
        return apprenantService.getAllApprenants();
    }

    @GetMapping("/{id}")
    public Apprenant getApprenantById(@PathVariable Long id) {
        return apprenantService.getApprenantById(id);
    }

    @PostMapping("/register")
    public Apprenant registerApprenant(@RequestBody Apprenant apprenant) {
        return apprenantService.saveApprenant(apprenant);
    }

    @DeleteMapping("/{id}")
    public void deleteApprenant(@PathVariable Long id) {
        apprenantService.deleteApprenant(id);
    }
}
