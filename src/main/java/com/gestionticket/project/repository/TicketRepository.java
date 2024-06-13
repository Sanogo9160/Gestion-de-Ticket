package com.gestionticket.project.repository;

import com.gestionticket.project.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository  extends JpaRepository <Ticket, Long>{
}

