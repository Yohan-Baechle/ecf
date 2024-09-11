package com.yb.sparadrap.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.time.LocalDate;
import java.util.Map;

/**
 * Classe représentant un achat dans l'application. Chaque achat peut contenir plusieurs médicaments,
 * un montant total, et éventuellement une ordonnance prescrite par un médecin.
 */
public class Purchase {

    private final ObjectProperty<Customer> customer;
    private final ObjectProperty<LocalDate> purchaseDate;
    private final MapProperty<Medication, Integer> medicationBasket; // Panier de médicaments et quantités
    private final DoubleProperty totalAmount;
    private final ObjectProperty<Doctor> prescribingDoctor; // Médecin prescripteur
    private final ObjectProperty<LocalDate> prescriptionDate; // Date de prescription

    /**
     * Constructeur par défaut initialisant les propriétés avec des valeurs par défaut.
     */
    public Purchase() {
        this.customer = new SimpleObjectProperty<>();
        this.purchaseDate = new SimpleObjectProperty<>();
        this.medicationBasket = new SimpleMapProperty<>(FXCollections.observableHashMap()); // Initialisation du panier vide
        this.totalAmount = new SimpleDoubleProperty(0.0); // Initialisation de totalAmount à 0
        this.prescribingDoctor = new SimpleObjectProperty<>();
        this.prescriptionDate = new SimpleObjectProperty<>();
    }

    /**
     * Constructeur permettant d'initialiser un achat avec toutes les informations nécessaires.
     *
     * @param customer          Le client associé à cet achat.
     * @param purchaseDate      La date de l'achat.
     * @param medications       Le panier de médicaments (médicament -> quantité).
     * @param prescribingDoctor Le médecin prescripteur (si applicable).
     * @param prescriptionDate  La date de prescription (si applicable).
     */
    public Purchase(Customer customer, LocalDate purchaseDate, Map<Medication, Integer> medications,
                    Doctor prescribingDoctor, LocalDate prescriptionDate) {
        this.customer = new SimpleObjectProperty<>(customer);
        this.purchaseDate = new SimpleObjectProperty<>(purchaseDate);
        this.medicationBasket = new SimpleMapProperty<>(FXCollections.observableHashMap()); // Initialisation du panier
        this.medicationBasket.putAll(medications); // Remplir le panier après l'initialisation
        this.totalAmount = new SimpleDoubleProperty(0.0); // Assurez-vous d'initialiser totalAmount avant de calculer
        calculateTotalAmount(); // Calculer le total après initialisation du panier
        this.prescribingDoctor = new SimpleObjectProperty<>(prescribingDoctor);
        this.prescriptionDate = new SimpleObjectProperty<>(prescriptionDate);
    }

    // Méthode pour ajouter un médicament au panier
    public void addMedication(Medication medication, int quantity) {
        medicationBasket.put(medication, quantity);
        calculateTotalAmount(); // Recalculer le total après ajout
    }

    // Propriétés observables + Getters et Setters
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

    public MapProperty<Medication, Integer> medicationBasketProperty() {
        return medicationBasket;
    }

    public Map<Medication, Integer> getMedicationBasket() {
        return medicationBasket.get();
    }

    public void setMedicationBasket(Map<Medication, Integer> medicationBasket) {
        this.medicationBasket.get().clear(); // On vide l'ancien panier
        this.medicationBasket.get().putAll(medicationBasket); // On ajoute le nouveau panier
        calculateTotalAmount(); // Recalculer le total lorsqu'un nouveau panier est défini
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

    // Propriétés liées à l'ordonnance (prescription)
    public ObjectProperty<Doctor> prescribingDoctorProperty() {
        return prescribingDoctor;
    }

    public Doctor getPrescribingDoctor() {
        return prescribingDoctor.get();
    }

    public void setPrescribingDoctor(Doctor prescribingDoctor) {
        this.prescribingDoctor.set(prescribingDoctor);
    }

    public ObjectProperty<LocalDate> prescriptionDateProperty() {
        return prescriptionDate;
    }

    public LocalDate getPrescriptionDate() {
        return prescriptionDate.get();
    }

    public void setPrescriptionDate(LocalDate prescriptionDate) {
        this.prescriptionDate.set(prescriptionDate);
    }

    /**
     * Méthode pour recalculer le montant total basé sur les prix des médicaments et les quantités achetées.
     */
    public double calculateTotalAmount() {
        double total = 0.0;
        for (Map.Entry<Medication, Integer> entry : medicationBasket.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        this.totalAmount.set(total);
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Achat effectué par ")
                .append(customer.get().getFirstName()).append(" ").append(customer.get().getLastName())
                .append(" le ").append(purchaseDate.get())
                .append(".\nMédicaments : ");

        for (Map.Entry<Medication, Integer> entry : medicationBasket.entrySet()) {
            sb.append(entry.getKey().getName())
                    .append(" (Quantité : ").append(entry.getValue()).append("), ");
        }

        sb.append("\nMontant total : ").append(String.format("%.2f €", totalAmount.get()));

        if (prescribingDoctor.get() != null) {
            sb.append("\nPrescrit par le Dr. ")
                    .append(prescribingDoctor.get().getFirstName()).append(" ").append(prescribingDoctor.get().getLastName())
                    .append(" (Numéro d'inscription : ").append(prescribingDoctor.get().getRegistrationNumber()).append(")")
                    .append(" le ").append(prescriptionDate.get());
        } else {
            sb.append("\nAucune prescription médicale.");
        }

        return sb.toString();
    }
}
