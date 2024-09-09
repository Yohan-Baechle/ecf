package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Doctor;
import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.Purchase;
import com.yb.sparadrap.model.store.CustomerDataStore;
import com.yb.sparadrap.model.store.DoctorDataStore;
import com.yb.sparadrap.model.store.MedicationDataStore;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class PurchaseFormController {

    @FXML
    private ComboBox<Customer> customerComboBox;
    @FXML
    private ComboBox<String> purchaseTypeComboBox;
    @FXML
    private ComboBox<Medication> medicationComboBox;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField unitPriceField;
    @FXML
    private TextField totalPriceField;
    @FXML
    private Label medicationErrorLabel;
    @FXML
    private Label quantityErrorLabel;

    // Champs liés à l'ordonnance
    @FXML
    private ComboBox<Doctor> prescribingDoctorComboBox;
    @FXML
    private DatePicker prescriptionDatePicker;
    @FXML
    private Label customerErrorLabel;
    @FXML
    private Label prescribingDoctorErrorLabel;
    @FXML
    private Label prescriptionDateErrorLabel;

    private Map<TextField, Label> fieldErrorMap;

    @FXML
    public void initialize() {
        // Configurer les options du type d'achat
        purchaseTypeComboBox.getItems().addAll("Direct", "Avec ordonnance");
        purchaseTypeComboBox.setValue("Direct"); // Initialiser par défaut

        // Charger les médecins dans la ComboBox
        prescribingDoctorComboBox.setItems(DoctorDataStore.getInstance().getDoctors());

        // Charger les clients dans la ComboBox
        customerComboBox.setItems(CustomerDataStore.getInstance().getCustomers());

        // Charger les médicaments dans la ComboBox
        medicationComboBox.setItems(MedicationDataStore.getInstance().getMedications());

        // Différer la sélection du premier médicament jusqu'à ce que tous les éléments de l'UI soient chargés
        Platform.runLater(() -> {
            if (!medicationComboBox.getItems().isEmpty()) {
                medicationComboBox.getSelectionModel().selectFirst();  // Sélectionner le premier médicament
                Medication selectedMedication = medicationComboBox.getSelectionModel().getSelectedItem();
                if (selectedMedication != null) {
                    unitPriceField.setText(String.format("%.2f", selectedMedication.getPrice()));
                    updateTotalPrice();
                }
            }
        });

        // Mettre à jour le prix total lorsque la quantité change
        quantityField.textProperty().addListener((obs, oldValue, newValue) -> updateTotalPrice());

        // Gestion du type d'achat
        purchaseTypeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            boolean isPrescription = "Avec ordonnance".equals(newValue);

            // Activer ou désactiver les champs liés à l'ordonnance
            customerComboBox.setDisable(!isPrescription);
            prescribingDoctorComboBox.setDisable(!isPrescription);
            prescriptionDatePicker.setDisable(!isPrescription);
        });

        // Configuration du DatePicker
        prescriptionDatePicker.setConverter(new StringConverter<>() {
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
                    prescriptionDateErrorLabel.setText("Date invalide.");
                    return null;
                }
            }
        });

        // Mapping des champs de saisie avec leurs labels d'erreur
        fieldErrorMap = new HashMap<>();
        fieldErrorMap.put(quantityField, quantityErrorLabel);
    }


    public void setPurchase(Purchase purchase) {
        if (purchase != null) {
            medicationComboBox.setValue(purchase.getMedication());
            quantityField.setText(String.valueOf(purchase.getQuantity()));
            unitPriceField.setText(String.format("%.2f", purchase.getMedication().getPrice()));
            totalPriceField.setText(String.format("%.2f", purchase.getTotalAmount()));

            if (purchase.getPrescribingDoctor() != null) {
                purchaseTypeComboBox.setValue("Avec ordonnance");
                customerComboBox.setValue(purchase.getCustomer());
                prescribingDoctorComboBox.setValue(purchase.getPrescribingDoctor());
                prescriptionDatePicker.setValue(purchase.getPrescriptionDate());
            } else {
                purchaseTypeComboBox.setValue("Direct");
            }
        }
    }

    public Purchase getPurchase() {
        Customer selectedCustomer = customerComboBox.getValue();
        Medication selectedMedication = medicationComboBox.getValue();
        int quantity = Integer.parseInt(quantityField.getText().trim());
        Doctor prescribingDoctor = prescribingDoctorComboBox.getValue();
        LocalDate prescriptionDate = null;

        if ("Avec ordonnance".equals(purchaseTypeComboBox.getValue())) {
            prescriptionDate = prescriptionDatePicker.getValue();
        }

        return new Purchase(selectedCustomer, LocalDate.now(), selectedMedication, quantity, prescribingDoctor, prescriptionDate);
    }

    public boolean validateInputs() {
        clearErrorLabels();

        boolean isMedicationValid = medicationComboBox.getValue() != null;
        if (!isMedicationValid) {
            medicationErrorLabel.setText("Sélectionnez un médicament.");
        }

        boolean isQuantityValid = !quantityField.getText().trim().isEmpty();
        if (!isQuantityValid) {
            quantityErrorLabel.setText("La quantité est obligatoire.");
        }

        // Si "Avec ordonnance" est sélectionné, valider les champs supplémentaires
        boolean isPrescriptionValid = true;
        if ("Avec ordonnance".equals(purchaseTypeComboBox.getValue())) {
            boolean isCustomerValid = customerComboBox.getValue() != null;
            if (!isCustomerValid) {
                customerErrorLabel.setText("Sélectionnez un client.");
                isPrescriptionValid = false;
            }

            boolean isDoctorValid = prescribingDoctorComboBox.getValue() != null;
            if (!isDoctorValid) {
                prescribingDoctorErrorLabel.setText("Sélectionnez un médecin.");
                isPrescriptionValid = false;
            }

            if (prescriptionDatePicker.getValue() == null) {
                prescriptionDateErrorLabel.setText("La date de prescription est obligatoire.");
                isPrescriptionValid = false;
            }
        }

        return !isMedicationValid || !isQuantityValid || !isPrescriptionValid;
    }

    private boolean validateField(TextField field, String error) {
        if (error != null) {
            fieldErrorMap.get(field).setText(error);
            return false;
        }
        return true;
    }

    private void updateTotalPrice() {
        Medication selectedMedication = medicationComboBox.getValue();
        if (selectedMedication != null && !quantityField.getText().trim().isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityField.getText().trim());
                double totalPrice = quantity * selectedMedication.getPrice();
                totalPriceField.setText(String.format("%.2f", totalPrice));
            } catch (NumberFormatException e) {
                totalPriceField.setText("Erreur");
            }
        }
    }

    private void clearErrorLabels() {
        medicationErrorLabel.setText("");
        quantityErrorLabel.setText("");
        customerErrorLabel.setText("");
        prescribingDoctorErrorLabel.setText("");
        prescriptionDateErrorLabel.setText("");
    }
}