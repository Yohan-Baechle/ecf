package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Mutual;
import com.yb.sparadrap.model.enums.Department;
import com.yb.sparadrap.model.enums.MedicationCategory;
import com.yb.sparadrap.util.ValidationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Map;
import java.util.HashMap;

public class MutualFormController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField zipCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private ComboBox<Department> departmentComboBox;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField reimbursementRateField;
    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label streetErrorLabel;
    @FXML
    private Label zipCodeErrorLabel;
    @FXML
    private Label cityErrorLabel;
    @FXML
    private Label departmentErrorLabel;
    @FXML
    private Label phoneNumberErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label reimbursementRateErrorLabel;

    private Map<TextField, Label> fieldErrorMap;

    @FXML
    public void initialize() {
        // Mapping des champs de saisie avec leurs labels d'erreur
        fieldErrorMap = new HashMap<>();
        fieldErrorMap.put(nameField, nameErrorLabel);
        fieldErrorMap.put(streetField, streetErrorLabel);
        fieldErrorMap.put(zipCodeField, zipCodeErrorLabel);
        fieldErrorMap.put(cityField, cityErrorLabel);
        fieldErrorMap.put(departmentComboBox.getEditor(), departmentErrorLabel);
        fieldErrorMap.put(phoneNumberField, phoneNumberErrorLabel);
        fieldErrorMap.put(emailField, emailErrorLabel);
        fieldErrorMap.put(reimbursementRateField, reimbursementRateErrorLabel);
    }

    public void setMutual(Mutual mutual) {
        if (mutual != null) {
            nameField.setText(mutual.getName());
            if (mutual.getAddress() != null) {
                streetField.setText(mutual.getAddress().getStreet());
                zipCodeField.setText(mutual.getAddress().getPostalCode());
                cityField.setText(mutual.getAddress().getCity());
            }
            departmentComboBox.setValue(mutual.getDepartment());
            phoneNumberField.setText(mutual.getPhoneNumber());
            emailField.setText(mutual.getEmail());
            reimbursementRateField.setText(Double.toString(mutual.getReimbursementRate()));
        }
    }

    public Mutual getMutual() {
        // Récupérer les valeurs des champs du formulaire
        String name = nameField.getText().trim();
        Address address = new Address(streetField.getText().trim(), zipCodeField.getText().trim(), cityField.getText().trim());
        Department department = departmentComboBox.getSelectionModel().getSelectedItem();
        String phoneNumber = phoneNumberField.getText().trim();
        String email = emailField.getText().trim();
        double reimbursementRate = Double.parseDouble(reimbursementRateField.getText().trim());

        // Créer et retourner un objet Doctor avec les données du formulaire
        return new Mutual(name, address, department, phoneNumber, email, reimbursementRate);
    }


    public boolean validateInputs() {
        clearErrorLabels();

        // Validation des champs
        boolean isFirstNameValid = validateField(nameField, ValidationUtil.validateFirstName(nameField.getText().trim()));
        boolean isStreetValid = validateField(streetField, ValidationUtil.validateStreet(streetField.getText().trim()));
        boolean isZipCodeValid = validateField(zipCodeField, ValidationUtil.validateZipCode(zipCodeField.getText().trim()));
        boolean isCityValid = validateField(cityField, ValidationUtil.validateCity(cityField.getText().trim()));
        Department selectedDepartment = departmentComboBox.getValue();
        String departmentError = ValidationUtil.validateDepartment(selectedDepartment);
        boolean isDepartmentValid = (departmentError == null);
        if (!isDepartmentValid) {
            departmentErrorLabel.setText(departmentError);
        }
        boolean isPhoneNumberValid = validateField(phoneNumberField, ValidationUtil.validatePhoneNumber(phoneNumberField.getText().trim()));
        boolean isEmailValid = validateField(emailField, ValidationUtil.validateEmail(emailField.getText().trim()));
        boolean reimbursementRateValid = validateField(reimbursementRateField, ValidationUtil.validateReimbursementRate(reimbursementRateField.getText().trim()));

        return !isFirstNameValid || !isStreetValid || !isZipCodeValid || !isCityValid || !isDepartmentValid
                || !isPhoneNumberValid || !isEmailValid || !reimbursementRateValid;
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
