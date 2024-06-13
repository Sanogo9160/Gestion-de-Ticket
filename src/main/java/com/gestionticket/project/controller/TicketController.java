package com.gestionticket.project.controller;

import com.gestionticket.project.model.Ticket;
import com.gestionticket.project.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/creer")
    public ResponseEntity<Ticket> creerTicket(@RequestBody Ticket ticket) {
        Ticket newTicket = ticketService.creerTicket(ticket);
        return ResponseEntity.ok(newTicket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenirTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.obtenirTicket(id);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> listeTickets() {
        List<Ticket> tickets = ticketService.listeTickets();
        return ResponseEntity.ok(tickets);
    }

    @PutMapping("/{id}/traiter")
    public ResponseEntity<Ticket> traiterTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket updatedTicket = ticketService.traiterTicket(id, ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerTicket(@PathVariable Long id) {
        ticketService.supprimerTicket(id);
        return ResponseEntity.noContent().build();
    }
}
