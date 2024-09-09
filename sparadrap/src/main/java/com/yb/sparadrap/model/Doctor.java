package com.yb.sparadrap.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe représentant un médecin dans l'application.
 * Hérite de la classe Person et ajoute une propriété spécifique, le numéro d'enregistrement médical.
 */
public class Doctor extends Person {
    private final StringProperty registrationNumber;

    // Constructeur par défaut
    public Doctor(String firstName, String lastName, Address address, String phoneNumber, String email, String registrationNumber) {
        super(firstName, lastName, address, phoneNumber, email);
        this.registrationNumber = new SimpleStringProperty(registrationNumber);
    }

    // Propriétés observables + Getters et Setters
    public StringProperty registrationNumberProperty() {
        return registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber.get();
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber.set(registrationNumber);
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " " + getRegistrationNumber();
    }
}
