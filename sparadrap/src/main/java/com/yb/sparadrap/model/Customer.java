package com.yb.sparadrap.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

public class Customer extends Person {
    private final StringProperty socialSecurityNumber;
    private final ObjectProperty<LocalDate> birthDate;
    private final StringProperty mutual;
    private final StringProperty referringDoctor;

    // Constructeur par défaut
    public Customer() {
        super("", "", new Address("", "", ""), "", "");
        this.socialSecurityNumber = new SimpleStringProperty("");
        this.birthDate = new SimpleObjectProperty<>(LocalDate.now());
        this.mutual = new SimpleStringProperty("");
        this.referringDoctor = new SimpleStringProperty("");
    }

    // Constructeur avec paramètres
    public Customer(String firstName, String lastName, Address address, String phoneNumber, String email,
                    String socialSecurityNumber, LocalDate birthDate, String mutual, String referringDoctor) {
        super(firstName, lastName, address, phoneNumber, email);
        this.socialSecurityNumber = new SimpleStringProperty(socialSecurityNumber);
        this.birthDate = new SimpleObjectProperty<>(birthDate);
        this.mutual = new SimpleStringProperty(mutual);
        this.referringDoctor = new SimpleStringProperty(referringDoctor);
    }

    // Getters et setters
    public StringProperty socialSecurityNumberProperty() {
        return socialSecurityNumber;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber.get();
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber.set(socialSecurityNumber);
    }

    public ObjectProperty<LocalDate> birthDateProperty() {
        return birthDate;
    }

    public LocalDate getBirthDate() {
        return birthDate.get();
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate.set(birthDate);
    }

    public StringProperty mutualProperty() {
        return mutual;
    }

    public String getMutual() {
        return mutual.get();
    }

    public void setMutual(String mutual) {
        this.mutual.set(mutual);
    }

    public StringProperty referringDoctorProperty() {
        return referringDoctor;
    }

    public String getReferringDoctor() {
        return referringDoctor.get();
    }

    public void setReferringDoctor(String referringDoctor) {
        this.referringDoctor.set(referringDoctor);
    }
}
