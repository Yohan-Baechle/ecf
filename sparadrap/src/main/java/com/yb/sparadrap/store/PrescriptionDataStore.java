package com.yb.sparadrap.store;

import com.yb.sparadrap.model.*;
import com.yb.sparadrap.model.enums.MedicationCategory;
import com.yb.sparadrap.model.enums.Specialty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

/**
 * Singleton gérant le stockage des ordonnances dans l'application.
 * Utilise une liste observable pour stocker et manipuler les ordonnances.
 */
public class PrescriptionDataStore {
    private static PrescriptionDataStore instance;
    private final ObservableList<Prescription> prescriptions;

    /**
     * Constructeur privé pour le pattern Singleton.
     * Initialise la liste des ordonnances et ajoute des données fictives.
     */
    private PrescriptionDataStore() {
        prescriptions = FXCollections.observableArrayList();
        initializeSampleData(); // Initialiser des données fictives
    }

    /**
     * Retourne l'instance unique de PrescriptionDataStore.
     * Si l'instance n'existe pas encore, elle est créée.
     *
     * @return L'instance unique de PrescriptionDataStore.
     */
    public static PrescriptionDataStore getInstance() {
        if (instance == null) {
            instance = new PrescriptionDataStore();
        }
        return instance;
    }

    /**
     * Retourne la liste observable des ordonnances.
     *
     * @return La liste observable des ordonnances.
     */
    public ObservableList<Prescription> getPrescriptions() {
        return prescriptions;
    }

    /**
     * Ajoute une ordonnance à la liste.
     *
     * @param prescription L'ordonnance à ajouter.
     */
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    /**
     * Supprime une ordonnance de la liste.
     *
     * @param prescription L'ordonnance à supprimer.
     */
    public void removePrescription(Prescription prescription) {
        prescriptions.remove(prescription);
    }

    /**
     * Initialise quelques ordonnances fictives pour le stockage en utilisant les clients et médecins existants.
     */
    private void initializeSampleData() {
        // Récupérer les clients et les médecins existants
        CustomerDataStore customerStore = CustomerDataStore.getInstance();
        DoctorDataStore doctorStore = DoctorDataStore.getInstance();

        Customer patient1 = customerStore.getCustomers().get(0); // Jean Dupont
        Customer patient2 = customerStore.getCustomers().get(1); // Marie Lefevre

        // Création de médecins spécialistes avec spécialités
        Specialist specialist1 = new Specialist("Bernard", "Martin",
                new Address("10 rue de la Santé", "75001", "Paris"),
                "0612345678", "bernard.martin@example.com", "10123456789", Specialty.GENERALISTE);

        Specialist specialist2 = new Specialist("Anne", "Dupuis",
                new Address("5 avenue des Hôpitaux", "75002", "Paris"),
                "0623456789", "anne.dupuis@example.com", "10234567890", Specialty.CARDIOLOGIE);

        // Création de quelques exemples de médicaments
        Medication med1 = new Medication("Paracétamol", MedicationCategory.ANALGESIQUE, LocalDate.of(2010, 5, 1), 10, 2.5);
        Medication med2 = new Medication("Ibuprofène", MedicationCategory.ANTI_INFLAMMATOIRE, LocalDate.of(2012, 8, 15), 20, 3.0);

        // Ajout des prescriptions fictives
        prescriptions.addAll(
                new Prescription(LocalDate.now(), specialist1, patient1, List.of(med1, med2)), // Ordonnance pour Jean Dupont
                new Prescription(LocalDate.now().minusDays(10), specialist2, patient2, List.of(med2)) // Ordonnance pour Marie Lefevre
        );
    }

}
