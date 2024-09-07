package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.store.MutualDataStore;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.yb.sparadrap.util.ValidationUtils;

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

    @FXML
    private ButtonType saveButtonType;

    private Customer customer;

    @FXML
    public void initialize() {
        mutualComboBox.setItems(MutualDataStore.getInstance().getMutuals());

        clearErrorLabels();

        // Configuration du DatePicker
        birthDatePicker.setConverter(new StringConverter<LocalDate>() {
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

// Remplir les champs avec les informations du client si disponible
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

            // Gérer la date de naissance avec un LocalDate directement
            LocalDate birthDate = customer.getBirthDate();
            if (birthDate != null) {
                birthDatePicker.setValue(birthDate);  // Définir la valeur du DatePicker directement
            } else {
                birthDatePicker.setValue(null);
            }

            mutualComboBox.setValue(customer.getMutual());
            primaryDoctorComboBox.setValue(customer.getReferringDoctor());
        }

        Button saveButton = (Button) dialogPane.lookupButton(saveButtonType);
        if (saveButton != null) {
            saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                if (!validateInputs()) {
                    event.consume();
                }
            });
        }
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

            // Gestion de la date de naissance
            if (customer.getBirthDate() != null) {
                birthDatePicker.setValue(customer.getBirthDate());
            } else {
                birthDatePicker.setValue(null);
            }

            mutualComboBox.setValue(customer.getMutual());
            primaryDoctorComboBox.setValue(customer.getReferringDoctor());
        }
    }


    public Customer getCustomer() {
        if (customer == null) {
            customer = new Customer();
        }
        customer.setFirstName(firstNameField.getText().trim());
        customer.setLastName(lastNameField.getText().trim());

        customer.setAddress(new Address(streetField.getText().trim(), zipCodeField.getText().trim(), cityField.getText().trim()));
        customer.setPhoneNumber(phoneNumberField.getText().trim());
        customer.setEmail(emailField.getText().trim());
        customer.setSocialSecurityNumber(socialSecurityNumberField.getText().trim());

        // Gestion de la date de naissance
        if (birthDatePicker.getValue() != null) {
            customer.setBirthDate(birthDatePicker.getValue());
        }

        customer.setMutual(mutualComboBox.getValue());
        customer.setReferringDoctor(primaryDoctorComboBox.getValue());
        return customer;
    }


    public boolean validateInputs() {
        boolean valid = true;
        clearErrorLabels();

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String street = streetField.getText().trim();
        String zipCode = zipCodeField.getText().trim();
        String city = cityField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String email = emailField.getText().trim();
        String socialSecurityNumber = socialSecurityNumberField.getText().trim();

        // Validation du prénom
        if (!ValidationUtils.isValidName(firstName)) {
            firstNameErrorLabel.setText("Le prénom est invalide.");
            valid = false;
        }

        // Validation du nom
        if (!ValidationUtils.isValidName(lastName)) {
            lastNameErrorLabel.setText("Le nom est invalide.");
            valid = false;
        }

        // Validation de l'adresse
        if (!ValidationUtils.isValidStreet(street)) {
            streetErrorLabel.setText("L'adresse est requise.");
            valid = false;
        }

        // Validation du code postal
        if (!ValidationUtils.isValidZipCode(zipCode)) {
            postalCodeErrorLabel.setText("Code postal invalide.");
            valid = false;
        }

        // Validation de la ville
        if (!ValidationUtils.isValidCity(city)) {
            cityErrorLabel.setText("La ville est requise.");
            valid = false;
        }

        // Validation du numéro de téléphone
        if (!ValidationUtils.isValidPhoneNumber(phoneNumber)) {
            phoneNumberErrorLabel.setText("Numéro de téléphone invalide.");
            valid = false;
        }

        // Validation de l'email
        if (!ValidationUtils.isValidEmail(email)) {
            emailErrorLabel.setText("Email invalide.");
            valid = false;
        }

        // Validation du numéro de sécurité sociale
        if (!ValidationUtils.isValidSocialSecurityNumber(socialSecurityNumber)) {
            socialSecurityNumberErrorLabel.setText("Numéro de sécurité sociale invalide.");
            valid = false;
        }

        // Validation de la date de naissance
        if (birthDatePicker.getValue() == null) {
            birthDateErrorLabel.setText("La date de naissance est requise.");
            valid = false;
        }

        // Validation des champs facultatifs
        if (mutualComboBox.getValue() == null || mutualComboBox.getValue().trim().isEmpty()) {
            mutualErrorLabel.setText(""); // Pas d'erreur si facultatif
        }
        if (primaryDoctorComboBox.getValue() == null || primaryDoctorComboBox.getValue().trim().isEmpty()) {
            referringDoctorErrorLabel.setText(""); // Pas d'erreur si facultatif
        }

        return valid;
    }

    private void clearErrorLabels() {
        firstNameErrorLabel.setText("");
        lastNameErrorLabel.setText("");
        streetErrorLabel.setText("");
        postalCodeErrorLabel.setText("");
        cityErrorLabel.setText("");
        phoneNumberErrorLabel.setText("");
        emailErrorLabel.setText("");
        socialSecurityNumberErrorLabel.setText("");
        birthDateErrorLabel.setText("");
        mutualErrorLabel.setText("");
        referringDoctorErrorLabel.setText("");
    }
}