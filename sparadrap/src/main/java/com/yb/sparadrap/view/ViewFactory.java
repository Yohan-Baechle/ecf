package com.yb.sparadrap.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe de fabrique responsable de la création et de la récupération des différentes vues dans l'application.
 */
public class ViewFactory {
    private final StringProperty selectedMenuItem;
    private AnchorPane customerView;

    /**
     * Initialise la ViewFactory avec une propriété de menu sélectionné.
     */
    public ViewFactory() {
        this.selectedMenuItem = new SimpleStringProperty("");
    }

    /**
     * Obtient la propriété utilisée pour suivre l'élément de menu actuellement sélectionné.
     *
     * @return la propriété de l'élément de menu sélectionné.
     */
    public StringProperty getSelectedMenuItem() {
        return selectedMenuItem;
    }


    /**
     * Charge et retourne la vue de gestion des clients.
     * Si la vue a déjà été chargée, retourne la vue mise en cache.
     *
     * @return la vue de gestion des achats.
     */
    public AnchorPane getCustomerView() {
        if (customerView == null) {
            try {
                customerView = new FXMLLoader(getClass().getResource("/fxml/layout/Customer.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return customerView;
    }

    /**
     * Ouvre la fenêtre principale de l'application avec une taille spécifique.
     */
    public void showMainWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        createStage(loader, true, 1200, 800);
    }

    /**
     * Crée et configure une nouvelle fenêtre (stage) avec les paramètres spécifiés.
     *
     * @param loader    l'instance de FXMLLoader utilisée pour charger le fichier FXML.
     * @param resizable si la fenêtre doit être redimensionnable.
     * @param width     la largeur de la fenêtre.
     * @param height    la hauteur de la fenêtre.
     */
    private void createStage(FXMLLoader loader, boolean resizable, double width, double height) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("SPARADRAP");
        stage.setResizable(resizable);
        stage.sizeToScene();
        stage.setWidth(width);
        stage.setHeight(height);
        stage.show();
    }

    /**
     * Ferme la fenêtre (stage) fournie.
     *
     * @param stage la fenêtre à fermer.
     */
    public void closeStage(Stage stage) {
        stage.close();
    }
}
