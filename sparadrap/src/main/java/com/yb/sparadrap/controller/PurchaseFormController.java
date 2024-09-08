package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.Purchase;
import com.yb.sparadrap.model.store.CustomerDataStore;
import com.yb.sparadrap.model.store.MedicationDataStore;
import com.yb.sparadrap.util.ValidationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PurchaseFormController {

    @FXML
    private ComboBox<Customer> customerComboBox;
    @FXML
    private ComboBox<Medication> medicationComboBox;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField unitPriceField;
    @FXML
    private TextField totalPriceField;

    @FXML
    private Label clientErrorLabel;
    @FXML
    private Label medicationErrorLabel;
    @FXML
    private Label quantityErrorLabel;

    private Map<TextField, Label> fieldErrorMap;

    @FXML
    public void initialize() {
        // Charger les clients dans la ComboBox
        customerComboBox.setItems(CustomerDataStore.getInstance().getCustomers());

        // Charger les médicaments dans la ComboBox
        medicationComboBox.setItems(MedicationDataStore.getInstance().getMedications());

        // Rendre les champs de prix non modifiables
        unitPriceField.setEditable(false);
        totalPriceField.setEditable(false);

        // Lorsque le médicament est sélectionné, mettre à jour le prix unitaire
        medicationComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                unitPriceField.setText(String.format("%.2f", newValue.getPrice()));
                // Mettre à jour le prix total
                updateTotalPrice();
            }
        });

        // Lorsque la quantité change, recalculer le prix total
        quantityField.textProperty().addListener((obs, oldValue, newValue) -> updateTotalPrice());

        fieldErrorMap = new HashMap<>();
        fieldErrorMap.put(quantityField, quantityErrorLabel);
    }

    public void setPurchase(Purchase purchase) {
        if (purchase != null) {
            customerComboBox.setValue(purchase.getCustomer());
            medicationComboBox.setValue(purchase.getMedication());
            quantityField.setText(String.valueOf(purchase.getQuantity()));
            unitPriceField.setText(String.format("%.2f", purchase.getMedication().getPrice()));
            totalPriceField.setText(String.format("%.2f", purchase.getTotalAmount()));
        }
    }

    // Méthode pour récupérer les données du formulaire et créer un objet Purchase
    public Purchase getPurchase() {
        Customer selectedCustomer = customerComboBox.getValue();
        Medication selectedMedication = medicationComboBox.getValue();
        int quantity = Integer.parseInt(quantityField.getText().trim());

        return new Purchase(selectedCustomer, LocalDate.now(), selectedMedication, quantity);
    }

    public boolean validateInputs() {
        clearErrorLabels();

        // Validation des champs
        boolean isClientValid = customerComboBox.getValue() != null;
        boolean isMedicationValid = medicationComboBox.getValue() != null;
        boolean isQuantityValid = validateField(quantityField, ValidationUtil.validateQuantity(quantityField.getText().trim()));

        if (!isClientValid) {
            clientErrorLabel.setText("Sélectionnez un client.");
        }
        if (!isMedicationValid) {
            medicationErrorLabel.setText("Sélectionnez un médicament.");
        }

        return !isClientValid || !isMedicationValid || !isQuantityValid;
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
        fieldErrorMap.values().forEach(label -> label.setText(""));
        clientErrorLabel.setText("");
        medicationErrorLabel.setText("");
    }
}
