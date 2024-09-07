package com.yb.sparadrap.model;

import com.yb.sparadrap.view.ViewFactory;

/**
 * Classe Singleton servant de modèle central pour l'application.
 * Elle fournit un accès global à la ViewFactory, qui gère les vues de l'application.
 */
public class AppModel {
    private static AppModel instance;
    private final ViewFactory viewFactory;

    /**
     * Constructeur privé pour empêcher l'instanciation directe.
     * Initialise l'instance de ViewFactory.
     */
    private AppModel() {
        this.viewFactory = new ViewFactory();
        this.viewFactory.getSelectedMenuItem().set("purchase");
    }

    /**
     * Retourne l'unique instance d'AppModel, en la créant si nécessaire.
     *
     * @return l'instance singleton d'AppModel.
     */
    public static AppModel getInstance() {
        if (instance == null) {
            instance = new AppModel();
        }
        return instance;
    }

    /**
     * Retourne l'instance de ViewFactory, qui gère les vues de l'application.
     *
     * @return l'instance de ViewFactory.
     */
    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}
