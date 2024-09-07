package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.AppModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Contrôleur responsable de la gestion du menu de navigation.
 * Il gère les interactions de l'utilisateur avec les boutons du menu et met à jour la vue principale en conséquence.
 */
public class SidebarController implements Initializable {
    public Button customerBtn;

    /**
     * Initialise le contrôleur du menu en configurant les écouteurs d'événements pour les boutons du menu.
     *
     * @param url            La localisation utilisée pour résoudre les chemins relatifs pour l'objet racine, ou null si la localisation est inconnue.
     * @param resourceBundle Les ressources utilisées pour localiser l'objet racine, ou null si non applicable.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();

        // Met à jour le bouton sélectionné en fonction de l'élément de menu actuellement sélectionné
        updateSelectedMenu(AppModel.getInstance().getViewFactory().getSelectedMenuItem().get());

        // Écoute les changements de l'élément de menu sélectionné pour mettre à jour l'interface
        AppModel.getInstance().getViewFactory().getSelectedMenuItem().addListener((observable, oldValue, newValue) -> updateSelectedMenu(newValue));
    }

    /**
     * Ajoute des écouteurs d'événements aux boutons du menu.
     * Chaque écouteur met à jour l'élément de menu sélectionné dans le ViewFactory.
     */
    private void addListeners() {
        customerBtn.setOnAction(event -> onCustomer());
    }

    /**
     * Définit l'élément de menu sélectionné sur "customer", ce qui déclenche l'affichage de la vue de gestion des clients.
     */
    private void onCustomer() {
        AppModel.getInstance().getViewFactory().getSelectedMenuItem().set("customer");
    }

    /**
     * Met à jour le style des boutons du menu en fonction de l'élément de menu sélectionné.
     *
     * @param selectedMenu L'identifiant de l'élément de menu sélectionné.
     */
    private void updateSelectedMenu(String selectedMenu) {
        // Supprime la classe 'button-selected' de tous les boutons
        customerBtn.getStyleClass().remove("button-selected");

        // Ajoute la classe 'button-selected' au bouton correspondant
        switch (selectedMenu) {
            case "customer":
                customerBtn.getStyleClass().add("button-selected");
                break;
            default:
                break;
        }
    }
}