package com.yb.sparadrap.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;

public class Customer extends Person {
    private final StringProperty socialSecurityNumber;
    private final ObjectProperty<LocalDate> birthDate;
    private final ObjectProperty<Mutual> mutual;
    private final ObjectProperty<Doctor> referringDoctor;

    // Constructeur par défaut
    public Customer() {
        super("", "", new Address("", "", ""), "", "");
        this.socialSecurityNumber = new SimpleStringProperty("");
        this.birthDate = new SimpleObjectProperty<>(LocalDate.now());

        // Initialisation de mutual et referringDoctor avec des valeurs par défaut
        Address mutualAddress = new Address("Rue de la Mutualité", "75000", "Paris");
        this.mutual = new SimpleObjectProperty<>(new Mutual("Mutuelle par défaut", mutualAddress, "0102030405", "contact@mutuelle.fr", "Finance", 75.0));

        Address doctorAddress = new Address("Rue des Médecins", "75001", "Paris");
        this.referringDoctor = new SimpleObjectProperty<>(new Doctor("Docteur", "Martin", doctorAddress, "0102030406", "docteur.martin@medecin.fr", "123456"));
    }

    // Constructeur avec paramètres
    public Customer(String firstName, String lastName, Address address, String phoneNumber, String email,
                    String socialSecurityNumber, LocalDate birthDate, Mutual mutual, Doctor referringDoctor) {
        super(firstName, lastName, address, phoneNumber, email);
        this.socialSecurityNumber = new SimpleStringProperty(socialSecurityNumber);
        this.birthDate = new SimpleObjectProperty<>(birthDate);
        this.mutual = new SimpleObjectProperty<>(mutual);
        this.referringDoctor = new SimpleObjectProperty<>(referringDoctor);
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

    public ObjectProperty<Mutual> mutualProperty() {
        return mutual;
    }

    public Mutual getMutual() {
        return mutual.get();
    }

    public void setMutual(Mutual mutual) {
        this.mutual.set(mutual);
    }

    public ObjectProperty<Doctor> referringDoctorProperty() {
        return referringDoctor;
    }

    public Doctor getReferringDoctor() {
        return referringDoctor.get();
    }

    public void setReferringDoctor(Doctor referringDoctor) {
        this.referringDoctor.set(referringDoctor);
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " " + getSocialSecurityNumber();
    }
}
