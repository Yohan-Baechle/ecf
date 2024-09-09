package com.yb.sparadrap.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Représente une adresse avec la rue, le code postal et la ville.
 * Utilise des propriétés JavaFX pour permettre l'observation des modifications dans l'interface graphique.
 */
public class Address {
    private final StringProperty street;
    private final StringProperty postalCode;
    private final StringProperty city;


    // Constructeur par défaut
    public Address(String street, String postalCode, String city) {
        this.street = new SimpleStringProperty(street);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.city = new SimpleStringProperty(city);
    }


    // Propriétés observables + Getters et Setters
    public StringProperty streetProperty() {
        return street;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }


    @Override
    public String toString() {
        return street.get() + ", " + postalCode.get() + ", " + city.get();
    }
}
