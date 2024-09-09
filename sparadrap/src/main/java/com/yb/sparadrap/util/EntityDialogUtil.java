package com.yb.sparadrap.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

public class EntityDialogUtil {

    /**
     * Ouvre une boîte de dialogue pour ajouter ou modifier une entité.
     *
     * @param fxmlPath        Le chemin vers le fichier FXML du formulaire.
     * @param dialogTitle     Le titre de la boîte de dialogue.
     * @param controllerConfigurer Un consommateur pour configurer le contrôleur avec l'entité.
     * @param resultMapper    Un consommateur pour mapper l'entité à partir du contrôleur.
     * @param <T>             Le type de l'entité.
     * @param <C>             Le type du contrôleur associé à l'entité.
     * @return Un Optional contenant l'entité modifiée ou ajoutée.
     */
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
                    e.printStackTrace();
                }
            });

            dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? resultMapper.mapResult(controller) : null);

            return dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
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