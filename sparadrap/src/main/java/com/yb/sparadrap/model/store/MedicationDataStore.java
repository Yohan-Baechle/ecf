package com.yb.sparadrap.model.store;

import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.enums.MedicationCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MedicationDataStore {
    private static MedicationDataStore instance;
    private final ObservableList<Medication> medications;

    // Constructeur privé pour le pattern Singleton
    private MedicationDataStore() {
        medications = FXCollections.observableArrayList();
        // Initialiser des données d'exemple
        initializeSampleData();
    }

    // Méthode pour obtenir l'instance unique
    public static MedicationDataStore getInstance() {
        if (instance == null) {
            instance = new MedicationDataStore();
        }
        return instance;
    }

    // Méthode pour récupérer la liste des médicaments
    public ObservableList<Medication> getMedications() {
        return medications;
    }

    // Méthode pour ajouter un médicament
    public void addMedication(Medication medication) {
        medications.add(medication);
    }

    // Méthode pour supprimer un médicament
    public void removeMedication(Medication medication) {
        medications.remove(medication);
    }

    // Méthode pour initialiser quelques médicaments fictifs
    private void initializeSampleData() {
        medications.addAll(
                new Medication("Paracétamol", MedicationCategory.ANALGESIQUE, 2.5, 10),
                new Medication("Ibuprofène", MedicationCategory.ANTI_INFLAMMATOIRE, 3.0, 20),
                new Medication("Amoxicilline", MedicationCategory.ANTIBIOTIQUE, 7.5, 15),
                new Medication("Vaccin grippe", MedicationCategory.VACCIN, 25.0, 5),
                new Medication("Statine", MedicationCategory.STATINE, 12.0, 30)
        );
    }
}
