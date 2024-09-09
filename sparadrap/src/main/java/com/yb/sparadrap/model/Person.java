package com.yb.sparadrap.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe abstraite représentant une personne, utilisée comme base pour les classes
 * telles que Customer ou Doctor dans l'application.
 * Chaque personne possède un prénom, un nom, une adresse, un numéro de téléphone et un email.
 */
public abstract class Person {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final Address address;  // Utilisation de l'objet Address pour représenter l'adresse de la personne
    private final StringProperty phoneNumber;
    private final StringProperty email;

    // Constructeur par défaut
    public Person(String firstName, String lastName, Address address, String phoneNumber, String email) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = address;
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
    }

    // Propriétés observables + Getters et Setters
    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address.setStreet(address.getStreet());
        this.address.setPostalCode(address.getPostalCode());
        this.address.setCity(address.getCity());
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}
