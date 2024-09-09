package com.yb.sparadrap.model;

import com.yb.sparadrap.model.enums.MedicationCategory;
import javafx.beans.property.*;

public class Medication {

    private final StringProperty name;
    private final ObjectProperty<MedicationCategory> category;
    private final DoubleProperty price;
    private final IntegerProperty quantity;

    // Constructeur par défaut
    public Medication() {
        this.name = new SimpleStringProperty();
        this.category = new SimpleObjectProperty<>();
        this.price = new SimpleDoubleProperty();
        this.quantity = new SimpleIntegerProperty();
    }

    // Constructeur avec paramètres
    public Medication(String name, MedicationCategory category, double price, int quantity) {
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleObjectProperty<>(category);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    // Getters et Setters
    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObjectProperty<MedicationCategory> categoryProperty() {
        return category;
    }

    public MedicationCategory getCategory() {
        return category.get();
    }

    public void setCategory(MedicationCategory category) {
        this.category.set(category);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    // Méthode pour calculer le coût total pour ce médicament
    public double calculateTotalCost() {
        return getPrice() * getQuantity();
    }

    @Override
    public String toString() {
        return name.get();  // Affiche uniquement le nom du médicament
    }
}
