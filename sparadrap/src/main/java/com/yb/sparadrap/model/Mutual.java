package com.yb.sparadrap.model;

import com.yb.sparadrap.model.enums.Department;
import javafx.beans.property.*;

/**
 * Classe représentant une mutuelle dans l'application.
 * Chaque mutuelle possède un nom, une adresse, un numéro de téléphone, un email, un département et un taux de remboursement.
 */
public class Mutual {
    private final IntegerProperty id; // Ajout de l'ID
    private final StringProperty name;
    private final Address address;
    private final ObjectProperty<Department> department;
    private final StringProperty phoneNumber;
    private final StringProperty email;
    private final DoubleProperty reimbursementRate;

    // Constructeur avec paramètres
    public Mutual(String name, Address address, Department department, String phoneNumber, String email, double reimbursementRate) {
        this.id = new SimpleIntegerProperty(); // Initialisation de l'ID
        this.name = new SimpleStringProperty(name);
        this.address = address;
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.department = new SimpleObjectProperty<>(department);
        this.reimbursementRate = new SimpleDoubleProperty(reimbursementRate);
    }

    // Propriétés observables + Getters et Setters
    public IntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

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
        this.address.setZipCode(address.getZipCode());
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

    // Propriété et méthodes pour Department
    public ObjectProperty<Department> departmentProperty() {
        return department;
    }

    public Department getDepartment() {
        return department.get();
    }

    public void setDepartment(Department department) {
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
