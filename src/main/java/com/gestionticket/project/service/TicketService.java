package com.gestionticket.project.service;

import com.gestionticket.project.model.*;
import com.gestionticket.project.repository.TicketRepository;
import com.gestionticket.project.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    public Ticket creerTicket(Ticket ticket) {
        ticket.setStatut(Statut.OUVERT);
        Ticket newTicket = ticketRepository.save(ticket);

        // Créer une notification pour la création du ticket
        Notification notification = new Notification();
        notification.setTicket(newTicket);
        notificationService.envoyerNotificationCreationTicket(notification);

        // Envoyer un e-mail de notification
        Apprenant user = newTicket.getUser();
        if (user != null && user.getEmail() != null) {
            String subject = "Nouveau Ticket Créé: " + newTicket.getTitre();
            String text = "Un nouveau ticket a été créé avec les détails suivants:\n" +
                    "ID: " + newTicket.getId() + "\n" +
                    "Titre: " + newTicket.getTitre() + "\n" +
                    "Description: " + newTicket.getDescription() + "\n" +
                    "Catégorie: " + newTicket.getCategorie() + "\n" +
                    "Priorité: " + newTicket.getPriorite() + "\n" +
                    "Statut: " + newTicket.getStatut() + "\n" +
                    "Date de Création: " + newTicket.getDateCreation();
            emailService.sendEmail(user.getEmail(), subject, text);
        }
        return newTicket;
    }

    public Ticket obtenirTicket(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ticket avec id " + id + " est introuvable"));
    }

    public List<Ticket> listeTickets() {
        return ticketRepository.findAll();
    }

    public Ticket traiterTicket(Long id, Ticket ticket) {
        Ticket existingTicket = obtenirTicket(id);
        existingTicket.setStatut(ticket.getStatut());
        Ticket updatedTicket = ticketRepository.save(existingTicket);

        // Créer une notification pour la modification du ticket
        Notification notification = new Notification();
        notification.setTicket(updatedTicket);
        notificationService.envoyerNotificationModificationTicket(notification);

        // Envoyer un e-mail de notification
        String subject = "Ticket Mis à Jour: " + updatedTicket.getTitre();
        String text = "Le ticket avec les détails suivants a été mis à jour:\n" +
                "ID: " + updatedTicket.getId() + "\n" +
                "Titre: " + updatedTicket.getTitre() + "\n" +
                "Description: " + updatedTicket.getDescription() + "\n" +
                "Catégorie: " + updatedTicket.getCategorie() + "\n" +
                "Priorité: " + updatedTicket.getPriorite() + "\n" +
                "Statut: " + updatedTicket.getStatut() + "\n" +
                "Date de Mise à Jour: " + updatedTicket.getDateMiseAJour();
        emailService.sendEmail(updatedTicket.getUser().getEmail(), subject, text);

        return updatedTicket;
    }

    public void supprimerTicket(Long id) {
        Ticket ticket = obtenirTicket(id);
        ticketRepository.delete(ticket);

        // Créer une notification pour la suppression du ticket
        Notification notification = new Notification();
        notification.setTicket(ticket);
        notificationService.envoyerNotificationSuppressionTicket(notification);

        // Envoyer un e-mail de notification
        String subject = "Ticket Supprimé: " + ticket.getTitre();
        String text = "Le ticket avec les détails suivants a été supprimé:\n" +
                "ID: " + ticket.getId() + "\n" +
                "Titre: " + ticket.getTitre() + "\n" +
                "Description: " + ticket.getDescription() + "\n" +
                "Catégorie: " + ticket.getCategorie() + "\n" +
                "Priorité: " + ticket.getPriorite() + "\n" +
                "Statut: " + ticket.getStatut() + "\n" +
                "Date de Création: " + ticket.getDateCreation();
        emailService.sendEmail(ticket.getUser().getEmail(), subject, text);
    }

    public Ticket assignerFormateur(Long ticketId, String formateurNom) {
        Ticket ticket = obtenirTicket(ticketId);
        Formateur formateur = (Formateur) userRepository.findByNom(formateurNom)
                .orElseThrow(() -> new EntityNotFoundException("Aucun Formateur trouvé avec ce nom : " + formateurNom));
        ticket.setFormateur(formateur);
        return ticketRepository.save(ticket);
    }


}
