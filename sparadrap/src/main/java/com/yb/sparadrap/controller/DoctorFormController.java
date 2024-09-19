package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Doctor;
import com.yb.sparadrap.model.Specialist;
import com.yb.sparadrap.model.enums.Specialty;
import com.yb.sparadrap.util.ValidationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class DoctorFormController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField zipCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField registrationNumberField;
    @FXML
    private ComboBox<Specialty> specialtyComboBox;
    @FXML
    private Label firstNameErrorLabel;
    @FXML
    private Label lastNameErrorLabel;
    @FXML
    private Label streetErrorLabel;
    @FXML
    private Label zipCodeErrorLabel;
    @FXML
    private Label cityErrorLabel;
    @FXML
    private Label phoneNumberErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label registrationNumberErrorLabel;

    private Map<TextField, Label> fieldErrorMap;

    @FXML
    public void initialize() {
        // Mapping des champs de saisie avec leurs labels d'erreur
        fieldErrorMap = new HashMap<>();
        fieldErrorMap.put(firstNameField, firstNameErrorLabel);
        fieldErrorMap.put(lastNameField, lastNameErrorLabel);
        fieldErrorMap.put(streetField, streetErrorLabel);
        fieldErrorMap.put(zipCodeField, zipCodeErrorLabel);
        fieldErrorMap.put(cityField, cityErrorLabel);
        fieldErrorMap.put(phoneNumberField, phoneNumberErrorLabel);
        fieldErrorMap.put(emailField, emailErrorLabel);
        fieldErrorMap.put(registrationNumberField, registrationNumberErrorLabel);

        // Initialiser la ComboBox avec les spécialités
        specialtyComboBox.getItems().addAll(Specialty.values());
        specialtyComboBox.setValue(Specialty.GENERALISTE); // Sélectionner Généraliste par défaut
    }

    public Doctor getDoctor() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        Address address = new Address(streetField.getText().trim(), zipCodeField.getText().trim(), cityField.getText().trim());
        String phoneNumber = phoneNumberField.getText().trim();
        String email = emailField.getText().trim();
        String registrationNumber = registrationNumberField.getText().trim();
        Specialty specialty = specialtyComboBox.getValue();

        // Crée un Specialist si une spécialité est choisie, sinon un Doctor (Généraliste)
        if (specialty != Specialty.GENERALISTE) {
            return new Specialist(firstName, lastName, address, phoneNumber, email, registrationNumber, specialty);
        } else {
            return new Doctor(firstName, lastName, address, phoneNumber, email, registrationNumber);
        }
    }

    public void setDoctor(Doctor doctor) {
        if (doctor != null) {
            firstNameField.setText(doctor.getFirstName());
            lastNameField.setText(doctor.getLastName());
            if (doctor.getAddress() != null) {
                streetField.setText(doctor.getAddress().getStreet());
                zipCodeField.setText(doctor.getAddress().getZipCode());
                cityField.setText(doctor.getAddress().getCity());
            }
            phoneNumberField.setText(doctor.getPhoneNumber());
            emailField.setText(doctor.getEmail());
            registrationNumberField.setText(doctor.getRegistrationNumber());

            if (doctor instanceof Specialist) {
                specialtyComboBox.setValue(((Specialist) doctor).getSpecialty());
            } else {
                specialtyComboBox.setValue(Specialty.GENERALISTE);
            }
        }
    }

    public boolean validateInputs() {
        clearErrorLabels();

        boolean isFirstNameValid = validateField(firstNameField, ValidationUtil.validateFirstName(firstNameField.getText().trim()));
        boolean isLastNameValid = validateField(lastNameField, ValidationUtil.validateLastName(lastNameField.getText().trim()));
        boolean isStreetValid = validateField(streetField, ValidationUtil.validateStreet(streetField.getText().trim()));
        boolean isZipCodeValid = validateField(zipCodeField, ValidationUtil.validateZipCode(zipCodeField.getText().trim()));
        boolean isCityValid = validateField(cityField, ValidationUtil.validateCity(cityField.getText().trim()));
        boolean isPhoneNumberValid = validateField(phoneNumberField, ValidationUtil.validatePhoneNumber(phoneNumberField.getText().trim()));
        boolean isEmailValid = validateField(emailField, ValidationUtil.validateEmail(emailField.getText().trim()));
        boolean isRegistrationNumberValid = validateField(registrationNumberField, ValidationUtil.validateRegistrationNumber(registrationNumberField.getText().trim()));

        return !isFirstNameValid || !isLastNameValid || !isStreetValid || !isZipCodeValid || !isCityValid
                || !isPhoneNumberValid || !isEmailValid || !isRegistrationNumberValid;
    }

    private boolean validateField(TextField field, String error) {
        if (error != null) {
            fieldErrorMap.get(field).setText(error);
            return false;
        }
        return true;
    }

    private void clearErrorLabels() {
        fieldErrorMap.values().forEach(label -> label.setText(""));
    }
}
