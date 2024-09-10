package com.yb.sparadrap.model.store;

import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Doctor;
import com.yb.sparadrap.model.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Singleton gérant le stockage des médecins dans l'application.
 * Utilise une liste observable pour stocker et manipuler les médecins.
 */
public class DoctorDataStore {
    private static DoctorDataStore instance;
    private final ObservableList<Doctor> doctors;

    /**
     * Constructeur privé pour le singleton.
     * Initialise la liste des médecins et ajoute des données fictives.
     */
    private DoctorDataStore() {
        doctors = FXCollections.observableArrayList();
        initializeSampleData(); // Ajouter des données fictives
    }

    /**
     * Retourne l'instance du singleton DoctorDataStore.
     * Si l'instance n'existe pas encore, elle est créée.
     *
     * @return L'instance unique de DoctorDataStore.
     */
    public static DoctorDataStore getInstance() {
        if (instance == null) {
            instance = new DoctorDataStore();
        }
        return instance;
    }

    /**
     * Retourne la liste observable des médecins.
     *
     * @return La liste observable des médecins.
     */
    public ObservableList<Doctor> getDoctors() {
        return doctors;
    }

    /**
     * Ajoute un nouveau prescripteur à la liste.
     *
     * @param doctor Le prescripteur à ajouter.
     */
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    /**
     * Supprime un client de la liste.
     *
     * @param doctor Le prescripteur à supprimer.
     */
    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
    }

    /**
     * Recherche un médecin par son nom complet (prénom + nom).
     *
     * @param name Le nom complet du médecin (prénom + nom).
     * @return Le médecin correspondant, ou null si aucune correspondance n'est trouvée.
     */
    public Doctor getDoctorByName(String name) {
        for (Doctor doctor : doctors) {
            String fullName = doctor.getFirstName() + " " + doctor.getLastName();
            if (fullName.equals(name)) {
                return doctor;
            }
        }
        return null;
    }

    /**
     * Initialise les données fictives pour le stockage des médecins.
     */
    private void initializeSampleData() {
        doctors.addAll(
                new Doctor("Bernard", "Martin",
                        new Address("10 rue de la Santé", "75001", "Paris"),
                        "0612345678", "bernard.martin@example.com", "10123456789"),

                new Doctor("Anne", "Dupuis",
                        new Address("5 avenue des Hôpitaux", "75002", "Paris"),
                        "0623456789", "anne.dupuis@example.com", "10234567890"),

                new Doctor("Sophie", "Girard",
                        new Address("3 rue des Médecins", "69001", "Lyon"),
                        "0634567890", "sophie.girard@example.com", "10345678901"),

                new Doctor("Pierre", "Lefevre",
                        new Address("8 boulevard des Cliniques", "06000", "Nice"),
                        "0645678901", "pierre.lefevre@example.com", "10456789012"),

                new Doctor("Jacques", "Bernard",
                        new Address("12 rue de la Médecine", "13001", "Marseille"),
                        "0656789012", "jacques.bernard@example.com", "10567890123")
        );
    }
}
