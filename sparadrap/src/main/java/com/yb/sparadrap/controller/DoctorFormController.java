package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Doctor;
import com.yb.sparadrap.util.ValidationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Map;
import java.util.HashMap;

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
    private Label firstNameErrorLabel;
    @FXML
    private Label lastNameErrorLabel;
    @FXML
    private Label streetErrorLabel;
    @FXML
    private Label postalCodeErrorLabel;
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
        fieldErrorMap.put(zipCodeField, postalCodeErrorLabel);
        fieldErrorMap.put(cityField, cityErrorLabel);
        fieldErrorMap.put(phoneNumberField, phoneNumberErrorLabel);
        fieldErrorMap.put(emailField, emailErrorLabel);
        fieldErrorMap.put(registrationNumberField, registrationNumberErrorLabel);
    }

    public void setDoctor(Doctor doctor) {
        if (doctor != null) {
            firstNameField.setText(doctor.getFirstName());
            lastNameField.setText(doctor.getLastName());
            if (doctor.getAddress() != null) {
                streetField.setText(doctor.getAddress().getStreet());
                zipCodeField.setText(doctor.getAddress().getPostalCode());
                cityField.setText(doctor.getAddress().getCity());
            }
            phoneNumberField.setText(doctor.getPhoneNumber());
            emailField.setText(doctor.getEmail());
            registrationNumberField.setText(doctor.getRegistrationNumber());
        }
    }

    public Doctor getDoctor() {
        // Récupérer les valeurs des champs du formulaire
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        Address address = new Address(streetField.getText().trim(), zipCodeField.getText().trim(), cityField.getText().trim());
        String phoneNumber = phoneNumberField.getText().trim();
        String email = emailField.getText().trim();
        String registrationNumber = registrationNumberField.getText().trim();

        // Créer et retourner un objet Doctor avec les données du formulaire
        return new Doctor(firstName, lastName, address, phoneNumber, email, registrationNumber);
    }


    public boolean validateInputs() {
        clearErrorLabels();

        // Validation des champs
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
        // Réinitialiser tous les labels d'erreur associés aux champs de saisie
        fieldErrorMap.values().forEach(label -> label.setText(""));
    }
}
