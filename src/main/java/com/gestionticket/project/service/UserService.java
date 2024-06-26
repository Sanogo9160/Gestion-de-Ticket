package com.gestionticket.project.service;

import com.gestionticket.project.model.*;
import com.gestionticket.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ApprenantService apprenantService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private FormateurService formateurService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User saveUser(User user) {
        // Encode the user's password
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));

        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Optional<Role> dbRoleOpt = roleRepository.findByName(role.getName());
            if (dbRoleOpt.isPresent()) {
                roles.add(dbRoleOpt.get());
            } else {
                roles.add(role);
            }
        }
        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        // Détacher les rôles pour éviter les références partagées
        savedUser.setRoles(new HashSet<>());

        // Enregistrer les rôles spécifiques (Apprenant, Admin, Formateur)
        for (Role role : roles) {
            switch (role.getName()) {
                case "ROLE_APPRENANT":
                    Apprenant apprenant = new Apprenant();
                    apprenant.setNom(savedUser.getNom());
                    apprenant.setEmail(savedUser.getEmail());
                    apprenant.setMotDePasse(savedUser.getMotDePasse());
                    apprenant.setRoles(savedUser.getRoles());
                    apprenantService.saveApprenant(apprenant);
                    break;
                case "ROLE_ADMIN":
                    Admin admin = new Admin();
                    admin.setNom(savedUser.getNom());
                    admin.setEmail(savedUser.getEmail());
                    admin.setMotDePasse(savedUser.getMotDePasse());
                    admin.setRoles(savedUser.getRoles());
                    adminService.saveAdmin(admin);
                    break;
                case "ROLE_FORMATEUR":
                    Formateur formateur = new Formateur();
                    formateur.setNom(savedUser.getNom());
                    formateur.setEmail(savedUser.getEmail());
                    formateur.setMotDePasse(savedUser.getMotDePasse());
                    formateur.setRoles(savedUser.getRoles());
                    formateurService.saveFormateur(formateur);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown role: " + role.getName());
            }
        }

        return savedUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
