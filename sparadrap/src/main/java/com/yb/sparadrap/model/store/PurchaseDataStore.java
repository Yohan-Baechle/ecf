package com.yb.sparadrap.model.store;

import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.Purchase;
import com.yb.sparadrap.model.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class PurchaseDataStore {
    private static PurchaseDataStore instance;
    private final ObservableList<Purchase> purchases;

    private PurchaseDataStore() {
        purchases = FXCollections.observableArrayList();
        // Ajouter des données initiales au démarrage
        initializeSampleData();
    }

    public static PurchaseDataStore getInstance() {
        if (instance == null) {
            instance = new PurchaseDataStore();
        }
        return instance;
    }

    public ObservableList<Purchase> getPurchases() {
        return purchases;
    }

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    public void removePurchase(Purchase purchase) {
        purchases.remove(purchase);
    }

    // Méthode pour initialiser quelques achats fictifs
    private void initializeSampleData() {
        // Récupérer les instances de Customer et Medication à partir de leur store respectif
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
