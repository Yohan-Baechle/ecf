package com.yb.sparadrap.model;

import com.yb.sparadrap.model.enums.Specialty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Classe représentant un spécialiste dans l'application.
 * Hérite de la classe Doctor et ajoute une propriété spécifique, la spécialité du médecin.
 */
public class Specialist extends Doctor {
    private final ObjectProperty<Specialty> specialty;

    // Constructeur par défaut
    public Specialist(String firstName, String lastName, Address address, String phoneNumber, String email, String registrationNumber, Specialty specialty) {
        super(firstName, lastName, address, phoneNumber, email, registrationNumber);
        this.specialty = new SimpleObjectProperty<>(specialty);
    }

    // Propriétés observables + Getters et Setters pour la spécialité
    public ObjectProperty<Specialty> specialtyProperty() {
        return specialty;
    }

    public Specialty getSpecialty() {
        return specialty.get();
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty.set(specialty);
    }

    @Override
    public String toString() {
        return super.toString() + " - Spécialité : " + getSpecialty().getDisplayName();
    }
}
