package com.yb.sparadrap.model.store;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MutualDataStore {
    private static MutualDataStore instance;
    private final ObservableList<String> mutuals;

    private MutualDataStore() {
        mutuals = FXCollections.observableArrayList();
        // Ajouter des données initiales au démarrage
        initializeSampleData();
    }

    public static MutualDataStore getInstance() {
        if (instance == null) {
            instance = new MutualDataStore();
        }
        return instance;
    }

    public ObservableList<String> getMutuals() {
        return mutuals;
    }

    public void addMutual(String mutual) {
        mutuals.add(mutual);
    }

    public void removeMutual(String mutual) {
        mutuals.remove(mutual);
    }

    // Méthode pour initialiser quelques mutuelles fictives
    private void initializeSampleData() {
        mutuals.addAll(
                "Mutuelle A",
                "Mutuelle B",
                "Mutuelle C",
                "Mutuelle D",
                "Mutuelle E",
                "Mutuelle F",
                "Mutuelle G",
                "Mutuelle H",
                "Mutuelle I"
        );
    }
}
