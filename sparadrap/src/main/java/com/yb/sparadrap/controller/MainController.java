package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.AppModel;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Contrôleur principal responsable de la gestion de la mise en page principale de l'application.
 * Il change dynamiquement les vues au centre du BorderPane en fonction de l'interaction de l'utilisateur.
 */
public class MainController implements Initializable {

    public BorderPane mainPane;  // Conteneur racine pour le changement dynamique des vues

    /**
     * Initialise le contrôleur et met en place les écouteurs pour gérer les changements de vue en fonction de l'élément de menu sélectionné.
     *
     * @param url l'emplacement utilisé pour résoudre les chemins relatifs pour l'objet racine, ou null si l'emplacement n'est pas connu.
     * @param resourceBundle les ressources utilisées pour localiser l'objet racine, ou null si non applicable.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Écoute les changements dans l'élément de menu sélectionné et met à jour la vue en conséquence
        AppModel.getInstance().getViewFactory().getSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "customer" -> mainPane.setCenter(AppModel.getInstance().getViewFactory().getCustomerView());
                case "doctor" -> mainPane.setCenter(AppModel.getInstance().getViewFactory().getDoctorView());
                case "medication" -> mainPane.setCenter(AppModel.getInstance().getViewFactory().getMedicationView());
                default -> mainPane.setCenter(AppModel.getInstance().getViewFactory().getPurchaseView());
            }
        });

        // Définit la vue par défaut sur la page d'accueil lorsque l'application démarre
        mainPane.setCenter(AppModel.getInstance().getViewFactory().getPurchaseView());
    }
}
