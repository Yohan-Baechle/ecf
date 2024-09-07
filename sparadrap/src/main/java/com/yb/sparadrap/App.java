package com.yb.sparadrap;

import com.yb.sparadrap.model.AppModel;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Point d'entrée de l'application JavaFX SPARADRAP.
 * Cette classe est responsable du lancement de l'application et de l'affichage de la fenêtre principale.
 */
public class App extends Application {

    /**
     * Point d'entrée principal pour toutes les applications JavaFX.
     * Cette méthode est appelée lorsque le système est prêt à lancer l'application.
     *
     * @param stage la fenêtre principale de l'application, sur laquelle la scène de l'application peut être définie.
     */
    @Override
    public void start(Stage stage) {
        // Affiche la fenêtre principale lorsque l'application démarre
        AppModel.getInstance().getViewFactory().showMainWindow();
    }

    /**
     * Méthode principale qui lance l'application JavaFX.
     *
     * @param args arguments de la ligne de commande passés à l'application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
