package com.yb.sparadrap.model;

import com.yb.sparadrap.model.enums.MedicationCategory;

public class Medication {

    private String name;
    private MedicationCategory category;
    private double price;
    private int quantity;
    // Constructeur par défaut
    public Medication() {
    }

    // Constructeur avec paramètres
    public Medication(String name, MedicationCategory category, double price, int quantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters et Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MedicationCategory getCategory() {
        return category;
    }

    public void setCategory(MedicationCategory category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Méthode pour calculer le coût total pour ce médicament
    public double calculateTotalCost() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
