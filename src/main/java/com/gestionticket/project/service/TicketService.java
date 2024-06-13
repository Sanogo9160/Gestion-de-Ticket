package com.gestionticket.project.service;

import com.gestionticket.project.exception.ResourceNotFoundException;
import com.gestionticket.project.model.Ticket;
import com.gestionticket.project.model.Statut;
import com.gestionticket.project.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket creerTicket(Ticket ticket) {
        ticket.setStatut(Statut.OUVERT);
        return ticketRepository.save(ticket);
    }

    public Ticket obtenirTicket(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
    }

    public List<Ticket> listeTickets() {
        return ticketRepository.findAll();
    }

    public Ticket traiterTicket(Long id, Ticket ticket) {
        Ticket existingTicket = obtenirTicket(id);
        existingTicket.setStatut(ticket.getStatut());
        return ticketRepository.save(existingTicket);
    }

    public void supprimerTicket(Long id) {
        Ticket ticket = obtenirTicket(id);
        ticketRepository.delete(ticket);
    }
}
