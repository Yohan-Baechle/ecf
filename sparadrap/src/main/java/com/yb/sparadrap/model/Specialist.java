package com.yb.sparadrap.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Specialist extends Doctor {
    private final StringProperty specialty;

    public Specialist(String firstName, String lastName, Address address, String phoneNumber, String email, String registrationNumber, String specialty) {
        super(firstName, lastName, address, phoneNumber, email, registrationNumber);
        this.specialty = new SimpleStringProperty(specialty);
    }

    // Getters et setters
    public StringProperty specialtyProperty() {
        return specialty;
    }

    public String getSpecialty() {
        return specialty.get();
    }

    public void setSpecialty(String specialty) {
        this.specialty.set(specialty);
    }
}
