package com.yb.sparadrap.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

import java.time.LocalDate;
import java.util.List;

public class Prescription {

    private final ObjectProperty<LocalDate> prescriptionDate;
    private final ObjectProperty<Doctor> doctor;
    private final ObjectProperty<Customer> patient;
    private final ListProperty<Medication> medications;
    private final ObjectProperty<Specialist> specialist;

    // Constructeur sans spécialiste
    public Prescription(LocalDate prescriptionDate, Doctor doctor, Customer patient, List<Medication> medications) {
        this.prescriptionDate = new SimpleObjectProperty<>(prescriptionDate);
        this.doctor = new SimpleObjectProperty<>(doctor);
        this.patient = new SimpleObjectProperty<>(patient);
        this.medications = new SimpleListProperty<>(FXCollections.observableArrayList(medications));
        this.specialist = new SimpleObjectProperty<>(null); // Aucun spécialiste par défaut
    }

    // Constructeur avec spécialiste
    public Prescription(LocalDate prescriptionDate, Doctor doctor, Customer patient, List<Medication> medications, Specialist specialist) {
        this.prescriptionDate = new SimpleObjectProperty<>(prescriptionDate);
        this.doctor = new SimpleObjectProperty<>(doctor);
        this.patient = new SimpleObjectProperty<>(patient);
        this.medications = new SimpleListProperty<>(FXCollections.observableArrayList(medications));
        this.specialist = new SimpleObjectProperty<>(specialist);
    }

    // Propriétés observables + Getters et Setters
    public ObjectProperty<LocalDate> prescriptionDateProperty() {
        return prescriptionDate;
    }

    public LocalDate getPrescriptionDate() {
        return prescriptionDate.get();
    }

    public void setPrescriptionDate(LocalDate prescriptionDate) {
        this.prescriptionDate.set(prescriptionDate);
    }

    public ObjectProperty<Doctor> doctorProperty() {
        return doctor;
    }

    public Doctor getDoctor() {
        return doctor.get();
    }

    public void setDoctor(Doctor doctor) {
        this.doctor.set(doctor);
    }

    public ObjectProperty<Customer> patientProperty() {
        return patient;
    }

    public Customer getPatient() {
        return patient.get();
    }

    public void setPatient(Customer patient) {
        this.patient.set(patient);
    }

    public ListProperty<Medication> medicationsProperty() {
        return medications;
    }

    public List<Medication> getMedications() {
        return medications.get();
    }

    public void setMedications(List<Medication> medications) {
        this.medications.setAll(medications);
    }

    public ObjectProperty<Specialist> specialistProperty() {
        return specialist;
    }

    public Specialist getSpecialist() {
        return specialist.get();
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist.set(specialist);
    }

    @Override
    public String toString() {
        Doctor doctorValue = doctor.get();
        String specialty = doctorValue instanceof Specialist
                ? ((Specialist) doctorValue).getSpecialty().getDisplayName()
                : "Généraliste";

        return "Prescription{" +
                "date=" + prescriptionDate.get() +
                ", médecin=" + doctorValue.getFirstName() + " " + doctorValue.getLastName() +
                " (spécialité : " + specialty + ")" +
                ", patient=" + patient.get().getFirstName() + " " + patient.get().getLastName() +
                ", médicaments=" + medications.get() +
                '}';
    }
}
