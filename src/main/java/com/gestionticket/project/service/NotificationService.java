package com.gestionticket.project.service;

import com.gestionticket.project.model.Notification;
import com.gestionticket.project.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void envoyerNotificationCreationTicket(Notification notification) {
        notification.setContenu("Nouveau ticket créé: " + notification.getTicket().getTitre() +
                " (ID: " + notification.getTicket().getId() + ")");
        notificationRepository.save(notification);
    }

    public void envoyerNotificationModificationTicket(Notification notification) {
        notification.setContenu("Ticket mis à jour: " + notification.getTicket().getTitre() +
                " (ID: " + notification.getTicket().getId() + ")");
        notificationRepository.save(notification);
    }

    public void envoyerNotificationSuppressionTicket(Notification notification) {
        notification.setContenu("Ticket supprimé: " + notification.getTicket().getTitre() +
                " (ID: " + notification.getTicket().getId() + ")");
        notificationRepository.save(notification);
    }

}
