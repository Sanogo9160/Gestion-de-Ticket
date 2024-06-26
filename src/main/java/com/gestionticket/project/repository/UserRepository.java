package com.gestionticket.project.repository;
import com.gestionticket.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNom(String formateurUsername);
}