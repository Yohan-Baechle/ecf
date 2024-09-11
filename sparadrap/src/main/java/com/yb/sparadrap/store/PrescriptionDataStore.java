package com.yb.sparadrap.store;

import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Doctor;
import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.Prescription;
import com.yb.sparadrap.model.enums.MedicationCategory;
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

        Doctor doctor1 = doctorStore.getDoctors().get(0); // Bernard Martin
        Doctor doctor2 = doctorStore.getDoctors().get(1); // Anne Dupuis

        // Création de quelques exemples de médicaments
        Medication med1 = new Medication("Paracétamol", MedicationCategory.ANALGESIQUE, LocalDate.of(2010, 5, 1), 10, 2.5);
        Medication med2 = new Medication("Ibuprofène", MedicationCategory.ANTI_INFLAMMATOIRE, LocalDate.of(2012, 8, 15), 20, 3.0);

        // Ajout des prescriptions fictives
        prescriptions.addAll(
                new Prescription(LocalDate.now(), doctor1, patient1, List.of(med1, med2)), // Ordonnance pour Jean Dupont
                new Prescription(LocalDate.now().minusDays(10), doctor2, patient2, List.of(med2)) // Ordonnance pour Marie Lefevre
        );
    }
}
