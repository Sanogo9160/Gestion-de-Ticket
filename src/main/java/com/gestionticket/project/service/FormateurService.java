package com.gestionticket.project.service;

import com.gestionticket.project.model.Formateur;
import com.gestionticket.project.repository.FormateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormateurService {

    @Autowired
    private FormateurRepository formateurRepository;

    public List<Formateur> getAllFormateurs() {
        return formateurRepository.findAll();
    }

    public Formateur getFormateurById(Long id) {
        return formateurRepository.findById(id).orElse(null);
    }

    public Formateur saveFormateur(Formateur formateur) {
        return formateurRepository.save(formateur);
    }

    public void deleteFormateur(Long id) {
        formateurRepository.deleteById(id);
    }
}
