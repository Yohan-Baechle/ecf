package com.yb.sparadrap.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe représentant une mutuelle dans l'application.
 * Chaque mutuelle possède un nom, une adresse, un numéro de téléphone, un email, un département et un taux de remboursement.
 */
public class Mutual {
    private final StringProperty name;
    private final Address address;
    private final StringProperty phoneNumber;
    private final StringProperty email;
    private final StringProperty department;
    private final DoubleProperty reimbursementRate;

    // Constructeur par défaut
    public Mutual(String name, Address address, String phoneNumber, String email, String department, double reimbursementRate) {
        this.name = new SimpleStringProperty(name);
        this.address = address;
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.department = new SimpleStringProperty(department);
        this.reimbursementRate = new SimpleDoubleProperty(reimbursementRate);
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

    public StringProperty departmentProperty() {
        return department;
    }

    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public DoubleProperty reimbursementRateProperty() {
        return reimbursementRate;
    }

    public double getReimbursementRate() {
        return reimbursementRate.get();
    }

    public void setReimbursementRate(double reimbursementRate) {
        this.reimbursementRate.set(reimbursementRate);
    }

    @Override
    public String toString() {
        return getName();  // Affiche uniquement le nom de la mutuelle
    }
}
