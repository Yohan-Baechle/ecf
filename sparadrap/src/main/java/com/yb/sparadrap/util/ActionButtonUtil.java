package com.yb.sparadrap.util;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.function.Consumer;

/**
 * Classe utilitaire pour créer des boutons d'action (Éditer et Supprimer) dans les TableView.
 */
public class ActionButtonUtil {

    /**
     * Crée une HBox contenant les boutons d'édition et de suppression.
     *
     * @param editAction   L'action à effectuer lors du clic sur le bouton Éditer.
     * @param deleteAction L'action à effectuer lors du clic sur le bouton Supprimer.
     * @param <T>          Le type de l'entité manipulée.
     * @return Une HBox contenant les boutons Éditer et Supprimer.
     */
    public static <T> HBox createEditDeleteButtons(T entity, Consumer<T> editAction, Consumer<T> deleteAction) {
        Button editButton = new Button();
        Button deleteButton = new Button();

        editButton.setGraphic(new FontIcon(FontAwesomeSolid.PEN));
        deleteButton.setGraphic(new FontIcon(FontAwesomeSolid.TRASH));

        editButton.getStyleClass().add("button-warning");
        deleteButton.getStyleClass().add("button-destructive");

        editButton.setOnAction(event -> editAction.accept(entity));
        deleteButton.setOnAction(event -> deleteAction.accept(entity));

        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(editButton, deleteButton);

        return hBox;
    }
}
