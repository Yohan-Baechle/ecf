package com.yb.sparadrap.model;

import com.yb.sparadrap.model.store.MutualDataStore;
import com.yb.sparadrap.model.store.DoctorDataStore;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

/**
 * Classe représentant un client dans l'application.
 * Hérite de la classe Person et ajoute des propriétés spécifiques aux clients
 * telles que le numéro de sécurité sociale, la date de naissance, la mutuelle et le médecin référent.
 */
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

        // Initialisation de la mutuelle et du médecin à partir des données existantes
        this.mutual = new SimpleObjectProperty<>(MutualDataStore.getInstance().getMutualByName("Mutuelle Alpha"));
        this.referringDoctor = new SimpleObjectProperty<>(DoctorDataStore.getInstance().getDoctorByName("Dr. Bernard Martin"));
    }

    /**
     * Constructeur avec paramètres pour créer un client avec des informations spécifiques.
     *
     * @param firstName            Prénom du client.
     * @param lastName             Nom du client.
     * @param address              Adresse du client.
     * @param phoneNumber          Numéro de téléphone du client.
     * @param email                Email du client.
     * @param socialSecurityNumber Numéro de sécurité sociale du client.
     * @param birthDate            Date de naissance du client.
     * @param mutual               Mutuelle associée au client.
     * @param referringDoctor      Médecin référent du client.
     */
    public Customer(String firstName, String lastName, Address address, String phoneNumber, String email,
                    String socialSecurityNumber, LocalDate birthDate, Mutual mutual, Doctor referringDoctor) {
        super(firstName, lastName, address, phoneNumber, email);
        this.socialSecurityNumber = new SimpleStringProperty(socialSecurityNumber);
        this.birthDate = new SimpleObjectProperty<>(birthDate);
        this.mutual = new SimpleObjectProperty<>(mutual);
        this.referringDoctor = new SimpleObjectProperty<>(referringDoctor);
    }

    // Propriétés observables + Getters et Setters
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
