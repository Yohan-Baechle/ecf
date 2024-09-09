package com.yb.sparadrap.model.store;

import com.yb.sparadrap.model.Doctor;
import com.yb.sparadrap.model.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DoctorDataStore {
    private static DoctorDataStore instance;
    private final ObservableList<Doctor> doctors;

    private DoctorDataStore() {
        doctors = FXCollections.observableArrayList();
        initializeSampleData();
    }

    public static DoctorDataStore getInstance() {
        if (instance == null) {
            instance = new DoctorDataStore();
        }
        return instance;
    }

    public ObservableList<Doctor> getDoctors() {
        return doctors;
    }

    public Doctor getDoctorByName(String name) {
        for (Doctor doctor : doctors) {
            String fullName = doctor.getFirstName() + " " + doctor.getLastName();
            if (fullName.equals(name)) {
                return doctor;
            }
        }
        return null;
    }

    private void initializeSampleData() {
        doctors.addAll(
                new Doctor("Bernard", "Martin",
                        new Address("10 rue de la Santé", "75001", "Paris"),
                        "0612345678", "bernard.martin@example.com", "D001"),

                new Doctor("Anne", "Dupuis",
                        new Address("5 avenue des Hôpitaux", "75002", "Paris"),
                        "0623456789", "anne.dupuis@example.com", "D002"),

                new Doctor("Sophie", "Girard",
                        new Address("3 rue des Médecins", "69001", "Lyon"),
                        "0634567890", "sophie.girard@example.com", "D003"),

                new Doctor("Pierre", "Lefevre",
                        new Address("8 boulevard des Cliniques", "06000", "Nice"),
                        "0645678901", "pierre.lefevre@example.com", "D004"),

                new Doctor("Jacques", "Bernard",
                        new Address("12 rue de la Médecine", "13001", "Marseille"),
                        "0656789012", "jacques.bernard@example.com", "D005")
        );
    }
}
