package com.yb.sparadrap.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

public class EntityDialogUtil {

    public static <C> void openEntityDisplayDialog(String fxmlPath, String dialogTitle,
                                                   Consumer<C> controllerConfigurer) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(EntityDialogUtil.class.getResource(fxmlPath));
            DialogPane dialogPane = loader.load();

            // Obtenir le contrôleur
            C controller = loader.getController();
            controllerConfigurer.accept(controller);  // Configurer le contrôleur

            // Créer la boîte de dialogue
            Dialog<Void> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle(dialogTitle);
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK);

            dialog.showAndWait();
        } catch (IOException e) {
            // Afficher une alerte en cas d'erreur
            showErrorDialog("Erreur", "Impossible de charger la boîte de dialogue : " + dialogTitle, e.getMessage());
        }
    }

    public static <T, C> Optional<T> openEntityFormDialog(String fxmlPath, String dialogTitle,
                                                          Consumer<C> controllerConfigurer,
                                                          ResultMapper<T, C> resultMapper) {
        try {
            // Charger le formulaire FXML
            FXMLLoader loader = new FXMLLoader(EntityDialogUtil.class.getResource(fxmlPath));
            DialogPane dialogPane = loader.load();

            // Obtenir le contrôleur associé
            C controller = loader.getController();
            controllerConfigurer.accept(controller);  // Appliquer la configuration sur le contrôleur

            // Créer et configurer la boîte de dialogue
            Dialog<T> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle(dialogTitle);
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            Button saveButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                try {
                    if (controller.getClass().getMethod("validateInputs").invoke(controller).equals(Boolean.TRUE)) {
                        event.consume(); // Empêche la fermeture si la validation échoue
                    }
                } catch (Exception e) {
                    // Afficher une alerte en cas d'erreur de validation
                    showErrorDialog("Erreur de validation", "Une erreur est survenue lors de la validation des données", e.getMessage());
                }
            });

            dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? resultMapper.mapResult(controller) : null);

            return dialog.showAndWait();
        } catch (IOException e) {
            // Afficher une alerte en cas d'erreur
            showErrorDialog("Erreur", "Impossible de charger la boîte de dialogue : " + dialogTitle, e.getMessage());
            return Optional.empty();
        }
    }

    // Méthode pour afficher une boîte de dialogue d'erreur
    private static void showErrorDialog(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Interface fonctionnelle pour mapper le résultat d'un contrôleur.
     *
     * @param <T> Le type de l'entité à retourner.
     * @param <C> Le type du contrôleur.
     */
    @FunctionalInterface
    public interface ResultMapper<T, C> {
        T mapResult(C controller);
    }
}
