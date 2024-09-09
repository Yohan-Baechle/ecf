package com.yb.sparadrap.model.store;

import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.Purchase;
import com.yb.sparadrap.model.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * Singleton gérant le stockage des achats dans l'application.
 * Utilise une liste observable pour stocker et manipuler les achats.
 */
public class PurchaseDataStore {
    private static PurchaseDataStore instance;
    private final ObservableList<Purchase> purchases;

    /**
     * Constructeur privé pour le pattern Singleton.
     * Initialise la liste des achats et ajoute des données fictives.
     */
    private PurchaseDataStore() {
        purchases = FXCollections.observableArrayList();
        initializeSampleData(); // Initialiser des données fictives
    }

    /**
     * Retourne l'instance unique de PurchaseDataStore.
     * Si l'instance n'existe pas encore, elle est créée.
     *
     * @return L'instance unique de PurchaseDataStore.
     */
    public static PurchaseDataStore getInstance() {
        if (instance == null) {
            instance = new PurchaseDataStore();
        }
        return instance;
    }

    /**
     * Retourne la liste observable des achats.
     *
     * @return La liste observable des achats.
     */
    public ObservableList<Purchase> getPurchases() {
        return purchases;
    }

    /**
     * Ajoute un achat à la liste.
     *
     * @param purchase L'achat à ajouter.
     */
    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    /**
     * Supprime un achat de la liste.
     *
     * @param purchase L'achat à supprimer.
     */
    public void removePurchase(Purchase purchase) {
        purchases.remove(purchase);
    }

    /**
     * Initialise quelques achats fictifs pour le stockage.
     */
    private void initializeSampleData() {
        // Récupérer les instances de Customer et Medication à partir de leurs stores respectifs
        Customer customer1 = CustomerDataStore.getInstance().getCustomers().get(0);
        Customer customer2 = CustomerDataStore.getInstance().getCustomers().get(1);
        Medication medication1 = MedicationDataStore.getInstance().getMedications().get(0);
        Medication medication2 = MedicationDataStore.getInstance().getMedications().get(1);
        Medication medication3 = MedicationDataStore.getInstance().getMedications().get(2);

        // Récupérer les instances de Doctor à partir de DoctorDataStore
        Doctor doctor1 = DoctorDataStore.getInstance().getDoctors().get(0); // Dr. Bernard Martin
        Doctor doctor2 = DoctorDataStore.getInstance().getDoctors().get(1); // Dr. Anne Dupuis

        // Ajout des achats fictifs
        purchases.addAll(
                new Purchase(customer1, LocalDate.of(2024, 1, 15), medication1, 2, null, null), // Achat direct
                new Purchase(customer1, LocalDate.of(2024, 1, 20), medication2, 1, doctor2, LocalDate.of(2024, 1, 19)), // Avec ordonnance (Dr. Dupuis)
                new Purchase(customer2, LocalDate.of(2024, 2, 10), medication3, 3, null, null), // Achat direct
                new Purchase(customer2, LocalDate.of(2024, 3, 5), medication1, 5, doctor1, LocalDate.of(2024, 3, 3)) // Avec ordonnance (Dr. Martin)
        );
    }
}
