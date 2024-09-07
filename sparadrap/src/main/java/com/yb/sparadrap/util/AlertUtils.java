package com.yb.sparadrap.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class AlertUtils {

    /**
     * Met à jour le statut de l'application avec un message et un type d'alerte (success, info, warning, error).
     *
     * @param statusLabel Le label à mettre à jour.
     * @param message Le message à afficher dans le label de statut.
     * @param alertType Le type d'alerte à appliquer (success, info, warning, error).
     */
    public static void updateStatusLabel(Label statusLabel, String message, String alertType) {
        // Applique le message
        statusLabel.setText(message);

        // Supprime les anciennes classes et applique la nouvelle classe en fonction du type d'alerte
        statusLabel.getStyleClass().setAll("alert", "alert-" + alertType);

        // Définit un délai avant de vider le message après 3 secondes
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(3),
                ae -> {
                    statusLabel.setText("");
                    statusLabel.getStyleClass().clear(); // Réinitialise les classes de style après la disparition du message
                }
        ));
        timeline.play();
    }
}
