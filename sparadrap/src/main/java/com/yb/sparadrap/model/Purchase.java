package com.yb.sparadrap.model;

import java.time.LocalDate;
import java.util.List;

public class Purchase {

    private Customer customer; // Le client qui effectue l'achat
    private LocalDate purchaseDate; // La date de l'achat
    private List<Medication> medications; // La liste des médicaments ou articles achetés
    private double totalAmount; // Le montant total de l'achat

    // Constructeur par défaut
    public Purchase() {
    }

    // Constructeur avec paramètres
    public Purchase(Customer customer, LocalDate purchaseDate, List<Medication> medications, double totalAmount) {
        this.customer = customer;
        this.purchaseDate = purchaseDate;
        this.medications = medications;
        this.totalAmount = totalAmount;
    }

    // Getters et Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Méthode pour calculer le montant total basé sur les médicaments
    public void calculateTotalAmount() {
        this.totalAmount = medications.stream()
                .mapToDouble(Medication::getPrice) // Suppose que la classe Medication a une méthode getPrice
                .sum();
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "customer=" + customer +
                ", purchaseDate=" + purchaseDate +
                ", medications=" + medications +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
