package com.yb.sparadrap.model.store;

import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.Purchase;
import com.yb.sparadrap.model.enums.MedicationCategory;
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
        Customer customer1 = new Customer("Jean", "Dupont", null, "0612345678", "jean.dupont@example.com", "192073409812345", LocalDate.of(1980, 5, 12), "Mutuelle A", "Dr. Bernard Martin");
        Customer customer2 = new Customer("Marie", "Lefevre", null, "0623456789", "marie.lefevre@example.com", "283067509825674", LocalDate.of(1975, 3, 22), "Mutuelle B", "Dr. Anne Dupuis");

        // Création de médicaments
        Medication medication1 = new Medication("Paracétamol", MedicationCategory.ANALGESIQUE, 2.5, 500);
        Medication medication2 = new Medication("Ibuprofène", MedicationCategory.ANTI_INFLAMMATOIRE, 3.0, 400);
        Medication medication3 = new Medication("Amoxicilline", MedicationCategory.ANTIBIOTIQUE, 7.5, 1000);

        // Ajout des achats fictifs
        purchases.addAll(
                new Purchase(customer1, LocalDate.of(2024, 1, 15), medication1, 2),
                new Purchase(customer1, LocalDate.of(2024, 1, 20), medication2, 1),
                new Purchase(customer2, LocalDate.of(2024, 2, 10), medication3, 3),
                new Purchase(customer2, LocalDate.of(2024, 3, 5), medication1, 5)
        );
    }

}
