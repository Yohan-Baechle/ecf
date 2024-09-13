package com.yb.sparadrap.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe de fabrique responsable de la création et de la récupération des différentes vues dans l'application.
 */
public class ViewFactory {
    private final StringProperty selectedMenuItem;
    private AnchorPane purchaseView;
    private AnchorPane customerView;
    private AnchorPane doctorView;
    private AnchorPane medicationView;
    private AnchorPane mutualView;
    private AnchorPane prescriptionView;

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
     * Charge et retourne la vue de gestion des achats.
     * Si la vue a déjà été chargée, retourne la vue mise en cache.
     *
     * @return la vue de gestion des achats.
     */
    public AnchorPane getPurchaseView() {
        if (purchaseView == null) {
            try {
                purchaseView = new FXMLLoader(getClass().getResource("/fxml/layout/Purchase.fxml")).load();
            } catch (IOException e) {
                showErrorDialog("Erreur de chargement", "Impossible de charger la vue des achats.", e.getMessage());
            }
        }
        return purchaseView;
    }

    /**
     * Charge et retourne la vue de gestion des clients.
     * Si la vue a déjà été chargée, retourne la vue mise en cache.
     *
     * @return la vue de gestion des clients.
     */
    public AnchorPane getCustomerView() {
        if (customerView == null) {
            try {
                customerView = new FXMLLoader(getClass().getResource("/fxml/layout/Customer.fxml")).load();
            } catch (IOException e) {
                showErrorDialog("Erreur de chargement", "Impossible de charger la vue des clients.", e.getMessage());
            }
        }
        return customerView;
    }

    /**
     * Charge et retourne la vue de gestion des prescripteurs.
     * Si la vue a déjà été chargée, retourne la vue mise en cache.
     *
     * @return la vue de gestion des prescripteurs.
     */
    public AnchorPane getDoctorView() {
        if (doctorView == null) {
            try {
                doctorView = new FXMLLoader(getClass().getResource("/fxml/layout/Doctor.fxml")).load();
            } catch (IOException e) {
                showErrorDialog("Erreur de chargement", "Impossible de charger la vue des prescripteurs.", e.getMessage());
            }
        }
        return doctorView;
    }

    /**
     * Charge et retourne la vue de gestion des médicaments.
     * Si la vue a déjà été chargée, retourne la vue mise en cache.
     *
     * @return la vue de gestion des médicaments.
     */
    public AnchorPane getMedicationView() {
        if (medicationView == null) {
            try {
                medicationView = new FXMLLoader(getClass().getResource("/fxml/layout/Medication.fxml")).load();
            } catch (IOException e) {
                showErrorDialog("Erreur de chargement", "Impossible de charger la vue des médicaments.", e.getMessage());
            }
        }
        return medicationView;
    }

    /**
     * Charge et retourne la vue de gestion des mutuelles.
     * Si la vue a déjà été chargée, retourne la vue mise en cache.
     *
     * @return la vue de gestion des mutuelles.
     */
    public AnchorPane getMutualView() {
        if (mutualView == null) {
            try {
                mutualView = new FXMLLoader(getClass().getResource("/fxml/layout/Mutual.fxml")).load();
            } catch (IOException e) {
                showErrorDialog("Erreur de chargement", "Impossible de charger la vue des mutuelles.", e.getMessage());
            }
        }
        return mutualView;
    }

    /**
     * Charge et retourne la vue de gestion des ordonnances.
     * Si la vue a déjà été chargée, retourne la vue mise en cache.
     *
     * @return la vue de gestion des ordonnances.
     */
    public AnchorPane getPrescriptionView() {
        if (prescriptionView == null) {
            try {
                prescriptionView = new FXMLLoader(getClass().getResource("/fxml/layout/Prescription.fxml")).load();
            } catch (IOException e) {
                showErrorDialog("Erreur de chargement", "Impossible de charger la vue des ordonnances.", e.getMessage());
            }
        }
        return prescriptionView;
    }

    /**
     * Ouvre la fenêtre principale de l'application avec une taille spécifique.
     */
    public void showMainWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        createStage(loader);
    }

    /**
     * Crée et configure une nouvelle fenêtre (stage) avec les paramètres spécifiés.
     *
     * @param loader l'instance de FXMLLoader utilisée pour charger le fichier FXML.
     */
    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            showErrorDialog("Erreur", "Impossible de charger la fenêtre principale.", e.getMessage());
        }

        if (scene != null) {
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("SPARADRAP");
            stage.setResizable(true);
            stage.sizeToScene();
            stage.setWidth(1200);
            stage.setHeight(800);
            stage.show();
        }
    }

    /**
     * Affiche une boîte de dialogue d'erreur.
     *
     * @param title   Le titre de la boîte de dialogue.
     * @param header  L'en-tête du message d'erreur.
     * @param content Le contenu détaillé de l'erreur.
     */
    private void showErrorDialog(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
