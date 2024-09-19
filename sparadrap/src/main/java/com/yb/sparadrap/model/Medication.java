package com.yb.sparadrap.model;

import com.yb.sparadrap.model.enums.MedicationCategory;
import javafx.beans.property.*;
import java.time.LocalDate;

/**
 * Classe représentant un médicament dans l'application.
 * Chaque médicament a un nom, une catégorie,une date de mise en service, une quantité en stock et un prix unitaire.
 */
public class Medication {

    private final StringProperty name;
    private final ObjectProperty<MedicationCategory> category;
    private final DoubleProperty price;
    private final IntegerProperty quantity;
    private final ObjectProperty<LocalDate> launchDate;

    // Constructeur par défaut
    public Medication() {
        this.name = new SimpleStringProperty();
        this.category = new SimpleObjectProperty<>();
        this.price = new SimpleDoubleProperty();
        this.quantity = new SimpleIntegerProperty();
        this.launchDate = new SimpleObjectProperty<>();
    }

    // Constructeur avec paramètres
    public Medication(String name, MedicationCategory category, LocalDate launchDate, int quantity, double price) {
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleObjectProperty<>(category);
        this.launchDate = new SimpleObjectProperty<>(launchDate);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    // Propriétés observables + Getters et Setters
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

    public ObjectProperty<LocalDate> launchDateProperty() {
        return launchDate;
    }

    public LocalDate getLaunchDate() {
        return launchDate.get();
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate.set(launchDate);
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

    @Override
    public String toString() {
        return name.get();
    }
}
