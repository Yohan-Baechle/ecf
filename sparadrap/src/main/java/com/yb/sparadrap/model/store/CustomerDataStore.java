package com.yb.sparadrap.model.store;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * Singleton gérant le stockage des clients dans l'application.
 * Il utilise une liste observable pour stocker et manipuler les clients.
 */
public class CustomerDataStore {
    private static CustomerDataStore instance;
    private final ObservableList<Customer> customers;

    /**
     * Constructeur privé pour le singleton.
     * Initialise la liste des clients et ajoute des données fictives.
     */
    private CustomerDataStore() {
        customers = FXCollections.observableArrayList();
        initializeSampleData(); // Ajouter des données fictives
    }

    /**
     * Retourne l'instance du singleton CustomerDataStore.
     * Si l'instance n'existe pas encore, elle est créée.
     *
     * @return L'instance unique de CustomerDataStore.
     */
    public static CustomerDataStore getInstance() {
        if (instance == null) {
            instance = new CustomerDataStore();
        }
        return instance;
    }

    /**
     * Retourne la liste observable des clients.
     *
     * @return La liste observable des clients.
     */
    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Ajoute un nouveau client à la liste.
     *
     * @param customer Le client à ajouter.
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Supprime un client de la liste.
     *
     * @param customer Le client à supprimer.
     */
    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    /**
     * Initialise les données fictives pour le stockage de clients.
     * Utilise les données disponibles dans MutualDataStore et DoctorDataStore.
     */
    private void initializeSampleData() {
        // Récupérer des instances de Mutual et Doctor depuis leurs stores respectifs
        MutualDataStore mutualStore = MutualDataStore.getInstance();
        DoctorDataStore doctorStore = DoctorDataStore.getInstance();

        customers.addAll(
                new Customer("Jean", "Dupont",
                        new Address("12 rue de la Paix", "75002", "Paris"),
                        "0612345678", "jean.dupont@example.com",
                        "179025452847269", LocalDate.of(1980, 5, 12),
                        mutualStore.getMutualByName("Mutuelle A"), doctorStore.getDoctorByName("Dr. Bernard Martin")),

                new Customer("Marie", "Lefevre",
                        new Address("25 avenue des Champs-Élysées", "75008", "Paris"),
                        "0623456789", "marie.lefevre@example.com",
                        "283067509825620", LocalDate.of(1975, 3, 22),
                        mutualStore.getMutualByName("Mutuelle B"), doctorStore.getDoctorByName("Dr. Anne Dupuis")),

                new Customer("Paul", "Moreau",
                        new Address("14 rue des Fleurs", "69002", "Lyon"),
                        "0645678910", "paul.moreau@example.com",
                        "170023456789044", LocalDate.of(1990, 11, 3),
                        mutualStore.getMutualByName("Mutuelle C"), doctorStore.getDoctorByName("Dr. Sophie Girard")),

                new Customer("Lucie", "Durand",
                        new Address("58 boulevard Victor Hugo", "06000", "Nice"),
                        "0654321098", "lucie.durand@example.com",
                        "185089012345607", LocalDate.of(1985, 8, 15),
                        mutualStore.getMutualByName("Mutuelle D"), doctorStore.getDoctorByName("Dr. Pierre Lefevre")),

                new Customer("Pierre", "Dubois",
                        new Address("34 rue de la République", "13001", "Marseille"),
                        "0678912345", "pierre.dubois@example.com",
                        "197054308765467", LocalDate.of(1970, 12, 7),
                        mutualStore.getMutualByName("Mutuelle E"), doctorStore.getDoctorByName("Dr. Jacques Bernard")),

                new Customer("Sophie", "Martin",
                        new Address("45 rue des Lilas", "44000", "Nantes"),
                        "0678901234", "sophie.martin@example.com",
                        "185072309854338", LocalDate.of(1987, 6, 22),
                        mutualStore.getMutualByName("Mutuelle F"), doctorStore.getDoctorByName("Dr. Claire Dubois")),

                new Customer("Antoine", "Rousseau",
                        new Address("13 avenue des Acacias", "33000", "Bordeaux"),
                        "0678909876", "antoine.rousseau@example.com",
                        "197052408765101", LocalDate.of(1979, 11, 17),
                        mutualStore.getMutualByName("Mutuelle G"), doctorStore.getDoctorByName("Dr. Philippe Leroy")),

                new Customer("Isabelle", "Petit",
                        new Address("75 boulevard de la Liberté", "59000", "Lille"),
                        "0687654321", "isabelle.petit@example.com",
                        "186054309872511", LocalDate.of(1992, 2, 10),
                        mutualStore.getMutualByName("Mutuelle H"), doctorStore.getDoctorByName("Dr. Anne Lopez")),

                new Customer("Laurent", "Girard",
                        new Address("22 rue du Commerce", "69001", "Lyon"),
                        "0698765432", "laurent.girard@example.com",
                        "175084309654364", LocalDate.of(1965, 7, 25),
                        mutualStore.getMutualByName("Mutuelle I"), doctorStore.getDoctorByName("Dr. Charles Garnier"))
        );
    }
}
