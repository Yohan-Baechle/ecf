package com.yb.sparadrap.model;

import javafx.beans.property.*;
import java.time.LocalDate;

/**
 * Classe représentant un achat dans l'application. Chaque achat est associé à un client, un médicament,
 * une quantité, un montant total, et éventuellement une ordonnance prescrite par un médecin.
 */
public class Purchase {

    private final ObjectProperty<Customer> customer;
    private final ObjectProperty<LocalDate> purchaseDate;
    private final ObjectProperty<Medication> medication; // Un seul médicament par ligne
    private final IntegerProperty quantity;
    private final DoubleProperty totalAmount;

    // Champs pour l'ordonnance
    private final ObjectProperty<Doctor> prescribingDoctor; // Médecin prescripteur
    private final ObjectProperty<LocalDate> prescriptionDate; // Date de prescription

    /**
     * Constructeur par défaut initialisant les propriétés avec des valeurs par défaut.
     */
    public Purchase() {
        this.customer = new SimpleObjectProperty<>();
        this.purchaseDate = new SimpleObjectProperty<>();
        this.medication = new SimpleObjectProperty<>();
        this.quantity = new SimpleIntegerProperty(0);
        this.totalAmount = new SimpleDoubleProperty(0.0);
        this.prescribingDoctor = new SimpleObjectProperty<>();
        this.prescriptionDate = new SimpleObjectProperty<>();
    }

    /**
     * Constructeur permettant d'initialiser un achat avec toutes les informations nécessaires.
     *
     * @param customer          Le client associé à cet achat.
     * @param purchaseDate      La date de l'achat.
     * @param medication        Le médicament acheté.
     * @param quantity          La quantité de médicament achetée.
     * @param prescribingDoctor Le médecin prescripteur (si applicable).
     * @param prescriptionDate  La date de prescription (si applicable).
     */
    public Purchase(Customer customer, LocalDate purchaseDate, Medication medication, int quantity,
                    Doctor prescribingDoctor, LocalDate prescriptionDate) {
        this.customer = new SimpleObjectProperty<>(customer);
        this.purchaseDate = new SimpleObjectProperty<>(purchaseDate);
        this.medication = new SimpleObjectProperty<>(medication);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.totalAmount = new SimpleDoubleProperty(medication.getPrice() * quantity);
        this.prescribingDoctor = new SimpleObjectProperty<>(prescribingDoctor);
        this.prescriptionDate = new SimpleObjectProperty<>(prescriptionDate);
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

    public ObjectProperty<Medication> medicationProperty() {
        return medication;
    }

    public Medication getMedication() {
        return medication.get();
    }

    public void setMedication(Medication medication) {
        this.medication.set(medication);
        calculateTotalAmount(); // Recalculer le total lorsqu'un nouveau médicament est défini
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
        calculateTotalAmount(); // Recalculer le total lorsqu'une nouvelle quantité est définie
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
     * Méthode pour recalculer le montant total basé sur le prix du médicament et la quantité achetée.
     */
    public void calculateTotalAmount() {
        if (medication.get() != null) {
            this.totalAmount.set(medication.get().getPrice() * quantity.get());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Achat effectué par ")
                .append(customer.get().getFirstName()).append(" ").append(customer.get().getLastName())
                .append(" le ").append(purchaseDate.get())
                .append(".\nMédicament : ").append(medication.get().getName())
                .append(", Quantité : ").append(quantity.get())
                .append(", Montant total : ").append(String.format("%.2f €", totalAmount.get()));

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
