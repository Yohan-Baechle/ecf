package com.yb.sparadrap.store;

import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.enums.MedicationCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * Singleton gérant le stockage des médicaments dans l'application.
 * Utilise une liste observable pour stocker et manipuler les médicaments.
 */
public class MedicationDataStore {
    private static MedicationDataStore instance;
    private final ObservableList<Medication> medications;

    /**
     * Constructeur privé pour le pattern Singleton.
     * Initialise la liste des médicaments et ajoute des données fictives.
     */
    private MedicationDataStore() {
        medications = FXCollections.observableArrayList();
        initializeSampleData(); // Initialiser des données fictives
    }

    /**
     * Retourne l'instance unique de MedicationDataStore.
     * Si l'instance n'existe pas encore, elle est créée.
     *
     * @return L'instance unique de MedicationDataStore.
     */
    public static MedicationDataStore getInstance() {
        if (instance == null) {
            instance = new MedicationDataStore();
        }
        return instance;
    }

    /**
     * Retourne la liste observable des médicaments.
     *
     * @return La liste observable des médicaments.
     */
    public ObservableList<Medication> getMedications() {
        return medications;
    }

    /**
     * Ajoute un médicament à la liste.
     *
     * @param medication Le médicament à ajouter.
     */
    public void addMedication(Medication medication) {
        medications.add(medication);
    }

    /**
     * Supprime un médicament de la liste.
     *
     * @param medication Le médicament à supprimer.
     */
    public void removeMedication(Medication medication) {
        medications.remove(medication);
    }

    /**
     * Initialise quelques médicaments fictifs pour le stockage.
     */
    private void initializeSampleData() {
        medications.addAll(
                new Medication("Paracétamol", MedicationCategory.ANALGESIQUE, LocalDate.of(2010, 5, 1), 10, 2.5),
                new Medication("Ibuprofène", MedicationCategory.ANTI_INFLAMMATOIRE, LocalDate.of(2012, 8, 15), 20, 3.0),
                new Medication("Amoxicilline", MedicationCategory.ANTIBIOTIQUE, LocalDate.of(2005, 3, 10), 15, 7.5),
                new Medication("Vaccin grippe", MedicationCategory.VACCIN, LocalDate.of(2018, 9, 20), 5, 25.0),
                new Medication("Statine", MedicationCategory.STATINE, LocalDate.of(2015, 11, 30), 30, 12.0)
        );
    }

}
