package com.yb.sparadrap.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Purchase {

    private final ObjectProperty<Customer> customer;
    private final ObjectProperty<LocalDate> purchaseDate;
    private final ObjectProperty<Medication> medication; // Un seul médicament par ligne
    private final IntegerProperty quantity;
    private final DoubleProperty totalAmount;

    // Constructeur par défaut
    public Purchase() {
        this.customer = new SimpleObjectProperty<>();
        this.purchaseDate = new SimpleObjectProperty<>();
        this.medication = new SimpleObjectProperty<>();
        this.quantity = new SimpleIntegerProperty(0);
        this.totalAmount = new SimpleDoubleProperty(0.0);
    }

    // Constructeur avec paramètres
    public Purchase(Customer customer, LocalDate purchaseDate, Medication medication, int quantity) {
        this.customer = new SimpleObjectProperty<>(customer);
        this.purchaseDate = new SimpleObjectProperty<>(purchaseDate);
        this.medication = new SimpleObjectProperty<>(medication);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.totalAmount = new SimpleDoubleProperty(medication.getPrice() * quantity);
    }

    // Propriétés observables
    public ObjectProperty<Customer> customerProperty() {
        return customer;
    }

    public Customer getCustomer() {
        return customer.get();
    }

    public void setCustomer(Customer customer) {
        this.customer.set(customer);
    }

    public ObjectProperty<LocalDate> purchaseDateProperty() {
        return purchaseDate;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate.get();
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate.set(purchaseDate);
    }

    public ObjectProperty<Medication> medicationProperty() {
        return medication;
    }

    public Medication getMedication() {
        return medication.get();
    }

    public void setMedication(Medication medication) {
        this.medication.set(medication);
        calculateTotalAmount();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
        calculateTotalAmount();
    }

    public DoubleProperty totalAmountProperty() {
        return totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount.get();
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount.set(totalAmount);
    }

    // Méthode pour recalculer le montant total basé sur le prix du médicament et la quantité
    public void calculateTotalAmount() {
        if (medication.get() != null) {
            this.totalAmount.set(medication.get().getPrice() * quantity.get());
        }
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "customer=" + customer.get() +
                ", purchaseDate=" + purchaseDate.get() +
                ", medication=" + medication.get() +
                ", quantity=" + quantity.get() +
                ", totalAmount=" + totalAmount.get() +
                '}';
    }
}
