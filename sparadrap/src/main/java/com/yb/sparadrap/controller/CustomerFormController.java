package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.store.MutualDataStore;
import com.yb.sparadrap.util.ValidationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.HashMap;

public class CustomerFormController {

    @FXML
    private DialogPane dialogPane;

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
    private TextField socialSecurityNumberField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private ComboBox<String> mutualComboBox;
    @FXML
    private ComboBox<String> primaryDoctorComboBox;

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
    private Label socialSecurityNumberErrorLabel;
    @FXML
    private Label birthDateErrorLabel;
    @FXML
    private Label mutualErrorLabel;
    @FXML
    private Label referringDoctorErrorLabel;

    private Map<TextField, Label> fieldErrorMap;

    @FXML
    public void initialize() {
        mutualComboBox.setItems(MutualDataStore.getInstance().getMutuals());

        // Configuration du DatePicker
        birthDatePicker.setConverter(new StringConverter<>() {
            private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String dateString) {
                try {
                    return (dateString != null && !dateString.trim().isEmpty()) ? LocalDate.parse(dateString, dateFormatter) : null;
                } catch (DateTimeParseException e) {
                    birthDateErrorLabel.setText("Date invalide.");
                    return null;
                }
            }
        });

        // Mapping des champs de saisie avec leurs labels d'erreur
        fieldErrorMap = new HashMap<>();
        fieldErrorMap.put(firstNameField, firstNameErrorLabel);
        fieldErrorMap.put(lastNameField, lastNameErrorLabel);
        fieldErrorMap.put(streetField, streetErrorLabel);
        fieldErrorMap.put(zipCodeField, postalCodeErrorLabel);
        fieldErrorMap.put(cityField, cityErrorLabel);
        fieldErrorMap.put(phoneNumberField, phoneNumberErrorLabel);
        fieldErrorMap.put(emailField, emailErrorLabel);
        fieldErrorMap.put(socialSecurityNumberField, socialSecurityNumberErrorLabel);
    }

    public void setCustomer(Customer customer) {
        if (customer != null) {
            firstNameField.setText(customer.getFirstName());
            lastNameField.setText(customer.getLastName());
            if (customer.getAddress() != null) {
                streetField.setText(customer.getAddress().getStreet());
                zipCodeField.setText(customer.getAddress().getPostalCode());
                cityField.setText(customer.getAddress().getCity());
            }
            phoneNumberField.setText(customer.getPhoneNumber());
            emailField.setText(customer.getEmail());
            socialSecurityNumberField.setText(customer.getSocialSecurityNumber());
            birthDatePicker.setValue(customer.getBirthDate());
            mutualComboBox.setValue(customer.getMutual());
            primaryDoctorComboBox.setValue(customer.getReferringDoctor());
        }
    }

    public Customer getCustomer() {
        return new Customer(
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                new Address(streetField.getText().trim(), zipCodeField.getText().trim(), cityField.getText().trim()),
                phoneNumberField.getText().trim(),
                emailField.getText().trim(),
                socialSecurityNumberField.getText().trim(),
                birthDatePicker.getValue(),
                mutualComboBox.getValue(),
                primaryDoctorComboBox.getValue()
        );
    }

    public boolean validateInputs() {
        clearErrorLabels();

        // Validation des champs TextField en utilisant la classe utilitaire
        boolean isFirstNameValid = validateField(firstNameField, ValidationUtil.validateFirstName(firstNameField.getText().trim()));
        boolean isLastNameValid = validateField(lastNameField, ValidationUtil.validateLastName(lastNameField.getText().trim()));
        boolean isStreetValid = validateField(streetField, ValidationUtil.validateStreet(streetField.getText().trim()));
        boolean isZipCodeValid = validateField(zipCodeField, ValidationUtil.validateZipCode(zipCodeField.getText().trim()));
        boolean isCityValid = validateField(cityField, ValidationUtil.validateCity(cityField.getText().trim()));
        boolean isPhoneNumberValid = validateField(phoneNumberField, ValidationUtil.validatePhoneNumber(phoneNumberField.getText().trim()));
        boolean isEmailValid = validateField(emailField, ValidationUtil.validateEmail(emailField.getText().trim()));
        boolean isSSNValid = validateField(socialSecurityNumberField, ValidationUtil.validateSocialSecurityNumber(socialSecurityNumberField.getText().trim()));
        String birthDateError = ValidationUtil.validateBirthDate(birthDatePicker.getValue());
        boolean isBirthDateValid = (birthDateError == null);
        if (!isBirthDateValid) {
            birthDateErrorLabel.setText(birthDateError);
        }

        // Retourne 'true' uniquement si toutes les validations sont correctes
        return isFirstNameValid && isLastNameValid && isStreetValid && isZipCodeValid && isCityValid
                && isPhoneNumberValid && isEmailValid && isSSNValid && isBirthDateValid;
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
        birthDateErrorLabel.setText("");
        mutualErrorLabel.setText("");
        referringDoctorErrorLabel.setText("");
    }
}
