package com.yb.sparadrap.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Doctor extends Person {
    private final StringProperty registrationNumber;

    public Doctor(String firstName, String lastName, Address address, String phoneNumber, String email, String registrationNumber) {
        super(firstName, lastName, address, phoneNumber, email);
        this.registrationNumber = new SimpleStringProperty(registrationNumber);
    }

    // Getters et setters
    public StringProperty registrationNumberProperty() {
        return registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber.get();
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber.set(registrationNumber);
    }
}
