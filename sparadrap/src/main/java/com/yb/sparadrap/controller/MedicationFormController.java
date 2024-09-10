package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.*;
import com.yb.sparadrap.model.enums.MedicationCategory;
import com.yb.sparadrap.model.store.DoctorDataStore;
import com.yb.sparadrap.model.store.MutualDataStore;
import com.yb.sparadrap.util.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.HashMap;

public class MedicationFormController {

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<MedicationCategory> categoryComboBox;
    @FXML
    private DatePicker launchDatePicker;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;
    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label categoryErrorLabel;
    @FXML
    private Label launchDateErrorLabel;
    @FXML
    private Label quantityErrorLabel;
    @FXML
    private Label priceErrorLabel;

    private Map<TextField, Label> fieldErrorMap;

    @FXML
    public void initialize() {
        // Charger les données dans la comboBox des catégories
        categoryComboBox.setItems(FXCollections.observableArrayList(MedicationCategory.values()));

        // Configuration du DatePicker
        launchDatePicker.setConverter(new StringConverter<>() {
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
                    launchDateErrorLabel.setText("Date invalide.");
                    return null;
                }
            }
        });

        // Mapping des champs de saisie avec leurs labels d'erreur
        fieldErrorMap = new HashMap<>();
        fieldErrorMap.put(nameField, nameErrorLabel);
        fieldErrorMap.put(categoryComboBox.getEditor(), categoryErrorLabel);
        fieldErrorMap.put(launchDatePicker.getEditor(), launchDateErrorLabel);
        fieldErrorMap.put(quantityField, quantityErrorLabel);
        fieldErrorMap.put(priceField, priceErrorLabel);
    }

    public void setMedication(Medication medication) {
        if (medication != null) {
            nameField.setText(medication.getName());
            categoryComboBox.setValue(medication.getCategory());
            launchDatePicker.setValue(medication.getLaunchDate());
            quantityField.setText(Integer.toString(medication.getQuantity()));
            priceField.setText(Double.toString(medication.getPrice()));
        }
    }

    public Medication getMedications() {
        // Récupérer les valeurs des champs du formulaire
        String name = nameField.getText().trim();
        MedicationCategory category = categoryComboBox.getValue();
        LocalDate launchDate = launchDatePicker.getValue();
        int quantity = Integer.parseInt(quantityField.getText().trim());
        double price = Double.parseDouble(priceField.getText().trim());

        return new Medication(name, category, launchDate, quantity, price);
    }



    public boolean validateInputs() {
        clearErrorLabels();

        // Validation des champs
        boolean isNameValid = validateField(nameField, ValidationUtil.validateName(nameField.getText().trim()));
        String categoryError = ValidationUtil.validateCategory(categoryComboBox.getValue());
        boolean isCategoryValid = (categoryError == null);
        if (!isCategoryValid) {
            categoryErrorLabel.setText(categoryError);
        }
        String launchDateError = ValidationUtil.validateLaunchDate(launchDatePicker.getValue());
        boolean isLaunchDateValid = (launchDateError == null);
        if (!isLaunchDateValid) {
            launchDateErrorLabel.setText(launchDateError);
        }
        boolean isQuantityValid = validateField(quantityField, ValidationUtil.validateQuantity(quantityField.getText().trim()));
        boolean isPriceValid = validateField(priceField, ValidationUtil.validatePrice(priceField.getText().trim()));

        // Retourne 'true' uniquement si toutes les validations sont correctes
        return !isNameValid || !isCategoryValid || !isLaunchDateValid || !isQuantityValid || !isPriceValid;
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
