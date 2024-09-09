package com.yb.sparadrap.model.store;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Doctor;
import com.yb.sparadrap.model.Mutual;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class CustomerDataStore {
    private static CustomerDataStore instance;
    private final ObservableList<Customer> customers;

    private CustomerDataStore() {
        customers = FXCollections.observableArrayList();
        // Ajouter des données initiales au démarrage
        initializeSampleData();
    }

    public static CustomerDataStore getInstance() {
        if (instance == null) {
            instance = new CustomerDataStore();
        }
        return instance;
    }

    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    // Méthode pour initialiser quelques clients fictifs
    private void initializeSampleData() {
        // Récupérer des instances de Mutual et Doctor depuis leurs stores respectifs
        MutualDataStore mutualStore = MutualDataStore.getInstance();
        DoctorDataStore doctorStore = DoctorDataStore.getInstance();

        customers.addAll(
                new Customer("Jean", "Dupont",
                        new Address("12 rue de la Paix", "75002", "Paris"),
                        "0612345678", "jean.dupont@example.com",
                        "192073409812345", LocalDate.of(1980, 5, 12),
                        mutualStore.getMutualByName("Mutuelle A"), doctorStore.getDoctorByName("Dr. Bernard Martin")),

                new Customer("Marie", "Lefevre",
                        new Address("25 avenue des Champs-Élysées", "75008", "Paris"),
                        "0623456789", "marie.lefevre@example.com",
                        "283067509825674", LocalDate.of(1975, 3, 22),
                        mutualStore.getMutualByName("Mutuelle B"), doctorStore.getDoctorByName("Dr. Anne Dupuis")),

                new Customer("Paul", "Moreau",
                        new Address("14 rue des Fleurs", "69002", "Lyon"),
                        "0645678910", "paul.moreau@example.com",
                        "170023456789012", LocalDate.of(1990, 11, 3),
                        mutualStore.getMutualByName("Mutuelle C"), doctorStore.getDoctorByName("Dr. Sophie Girard")),

                new Customer("Lucie", "Durand",
                        new Address("58 boulevard Victor Hugo", "06000", "Nice"),
                        "0654321098", "lucie.durand@example.com",
                        "185089012345678", LocalDate.of(1985, 8, 15),
                        mutualStore.getMutualByName("Mutuelle D"), doctorStore.getDoctorByName("Dr. Pierre Lefevre")),

                new Customer("Pierre", "Dubois",
                        new Address("34 rue de la République", "13001", "Marseille"),
                        "0678912345", "pierre.dubois@example.com",
                        "197054308765432", LocalDate.of(1970, 12, 7),
                        mutualStore.getMutualByName("Mutuelle E"), doctorStore.getDoctorByName("Dr. Jacques Bernard")),

                new Customer("Sophie", "Martin",
                        new Address("45 rue des Lilas", "44000", "Nantes"),
                        "0678901234", "sophie.martin@example.com",
                        "185072309854321", LocalDate.of(1987, 6, 22),
                        mutualStore.getMutualByName("Mutuelle F"), doctorStore.getDoctorByName("Dr. Claire Dubois")),

                new Customer("Antoine", "Rousseau",
                        new Address("13 avenue des Acacias", "33000", "Bordeaux"),
                        "0678909876", "antoine.rousseau@example.com",
                        "197052408765123", LocalDate.of(1979, 11, 17),
                        mutualStore.getMutualByName("Mutuelle G"), doctorStore.getDoctorByName("Dr. Philippe Leroy")),

                new Customer("Isabelle", "Petit",
                        new Address("75 boulevard de la Liberté", "59000", "Lille"),
                        "0687654321", "isabelle.petit@example.com",
                        "186054309872543", LocalDate.of(1992, 2, 10),
                        mutualStore.getMutualByName("Mutuelle H"), doctorStore.getDoctorByName("Dr. Anne Lopez")),

                new Customer("Laurent", "Girard",
                        new Address("22 rue du Commerce", "69001", "Lyon"),
                        "0698765432", "laurent.girard@example.com",
                        "175084309654312", LocalDate.of(1965, 7, 25),
                        mutualStore.getMutualByName("Mutuelle I"), doctorStore.getDoctorByName("Dr. Charles Garnier"))
        );
    }
}
