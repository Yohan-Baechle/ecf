package com.yb.sparadrap.util;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class DeleteUtil {

    /**
     * Affiche un dialogue de confirmation pour la suppression d'une entité.
     *
     * @param entity          L'entité à supprimer (par exemple, un client, un docteur, etc.).
     * @param deleteAction    L'action à effectuer en cas de confirmation de suppression.
     * @param successMessage  Le message de succès à afficher après suppression.
     * @param <T>             Le type de l'entité.
     */
    public static <T> void handleDelete(T entity, Consumer<T> deleteAction, String successMessage, Label statusLabel) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet élément ?");
        alert.setContentText("Cette action est irréversible.");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(DeleteUtil.class.getResource("/styles/global.css")).toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        FontIcon icon = new FontIcon(FontAwesomeSolid.INFO_CIRCLE);
        icon.setIconSize(48);
        icon.setIconColor(Color.web("#E53935"));
        alert.setGraphic(icon);

        ButtonType yesButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Button yesBtn = (Button) dialogPane.lookupButton(yesButton);
        yesBtn.setStyle("-fx-background-color: #F44336;");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            deleteAction.accept(entity); // Exécuter l'action de suppression
            AlertUtil.updateStatusLabel(statusLabel, successMessage, "success");
        }
    }

}
