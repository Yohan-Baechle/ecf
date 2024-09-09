package com.yb.sparadrap.model.store;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Mutual;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MutualDataStore {
    private final ObservableList<Mutual> mutuals = FXCollections.observableArrayList();

    private static MutualDataStore instance;

    private MutualDataStore() {
        // Ajouter des mutuelles fictives avec des objets Address, phoneNumber, email, department et remboursement
        mutuals.add(new Mutual("Mutuelle Alpha", new Address("10 Rue Alpha", "75001", "Paris"), "0102030405", "alpha@mutuelle.fr", "Finance", 80.0));
        mutuals.add(new Mutual("Mutuelle Beta", new Address("20 Rue Beta", "69001", "Lyon"), "0203040506", "beta@mutuelle.fr", "Santé", 90.0));
    }

    public Mutual getMutualByName(String name) {
        for (Mutual mutual : mutuals) {
            if (mutual.getName().equals(name)) {
                return mutual;
            }
        }
        return null;
    }

    public static MutualDataStore getInstance() {
        if (instance == null) {
            instance = new MutualDataStore();
        }
        return instance;
    }

    public ObservableList<Mutual> getMutuals() {
        return mutuals;
    }
}
