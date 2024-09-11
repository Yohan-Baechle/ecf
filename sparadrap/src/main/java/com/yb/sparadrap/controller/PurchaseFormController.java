package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Doctor;
import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.Purchase;
import com.yb.sparadrap.store.CustomerDataStore;
import com.yb.sparadrap.store.DoctorDataStore;
import com.yb.sparadrap.store.MedicationDataStore;
import com.yb.sparadrap.util.ValidationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PurchaseFormController {

    private final Map<Medication, Integer> medicationBasket = new HashMap<>();
    @FXML
    private ComboBox<Customer> customerComboBox;
    @FXML
    private ComboBox<Medication> medicationComboBox;
    @FXML
    private TextField quantityField;
    @FXML
    private TextArea medicationBasketArea;
    @FXML
    private TextField totalPriceField;
    @FXML
    private ComboBox<Doctor> prescribingDoctorComboBox;
    @FXML
    private DatePicker prescriptionDatePicker;
    @FXML
    private ComboBox<String> purchaseTypeComboBox;
    @FXML
    private Label medicationErrorLabel;
    @FXML
    private Label customerErrorLabel;
    @FXML
    private Label prescribingDoctorErrorLabel;
    @FXML
    private Label prescriptionDateErrorLabel;
    @FXML
    private Label quantityErrorLabel;

    @FXML
    public void initialize() {

        // Configurer les options du type d'achat
        purchaseTypeComboBox.getItems().addAll("Direct", "Avec ordonnance");
        purchaseTypeComboBox.setValue("Direct"); // Par défaut

        // Charger les clients et les médecins dans les ComboBoxes
        customerComboBox.setItems(CustomerDataStore.getInstance().getCustomers());
        prescribingDoctorComboBox.setItems(DoctorDataStore.getInstance().getDoctors());
        medicationComboBox.setItems(MedicationDataStore.getInstance().getMedications());

        // Désactiver les champs liés à l'ordonnance si l'achat est "Direct"
        purchaseTypeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            boolean isPrescription = "Avec ordonnance".equals(newValue);
            customerComboBox.setDisable(!isPrescription);
            prescribingDoctorComboBox.setDisable(!isPrescription);
            prescriptionDatePicker.setDisable(!isPrescription);
        });

        // Listener pour mettre à jour le prix unitaire et total lors de la sélection du médicament
        medicationComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                updateTotalPrice();
            }
        });

        // Listener pour mettre à jour le prix total lors du changement de quantité
        quantityField.textProperty().addListener((obs, oldValue, newValue) -> updateTotalPrice());

        // Initialisation de la zone de texte pour le panier
        medicationBasketArea.setEditable(false);
    }

    /**
     * Met à jour le champ TextArea qui affiche les médicaments du panier.
     *
     * @param medicationBasket Le panier de médicaments.
     */
    private void updateMedicationBasketArea(Map<Medication, Integer> medicationBasket) {
        if (medicationBasket != null && !medicationBasket.isEmpty()) {
            StringBuilder basketContent = new StringBuilder();
            for (Map.Entry<Medication, Integer> entry : medicationBasket.entrySet()) {
                basketContent.append(entry.getKey().getName())
                        .append(" (Quantité: ").append(entry.getValue()).append(")\n");
            }
            medicationBasketArea.setText(basketContent.toString());
        } else {
            medicationBasketArea.setText("Aucun médicament sélectionné");
        }
    }

    /**
     * Ajoute le médicament sélectionné dans le panier avec la quantité spécifiée.
     */
    @FXML
    private void handleAddMedicationToBasket() {
        // Vérification si un médicament est sélectionné
        Medication selectedMedication = medicationComboBox.getValue();
        if (selectedMedication == null) {
            medicationErrorLabel.setText("Veuillez sélectionner un médicament.");
            return;
        }

        // Vérification si la quantité est saisie
        String quantityText = quantityField.getText().trim();
        if (quantityText.isEmpty()) {
            quantityErrorLabel.setText("Veuillez entrer une quantité.");
            return;
        }

        try {
            // Tentative de conversion de la quantité en entier
            int quantity = Integer.parseInt(quantityText);

            if (quantity <= 0) {
                quantityErrorLabel.setText("La quantité doit être supérieure à zéro.");
                return;
            }

            // Si tout est valide, ajouter le médicament au panier
            medicationBasket.put(selectedMedication, quantity);
            updateMedicationBasketArea(medicationBasket);
            updateTotalPrice();

            // Effacer les erreurs une fois le médicament ajouté
            clearErrorLabels();

        } catch (NumberFormatException e) {
            // Si la conversion échoue, afficher un message d'erreur
            quantityErrorLabel.setText("La quantité doit être un nombre valide.");
        }
    }


    /**
     * Calcule et met à jour le prix total basé sur le panier.
     */
    private void updateTotalPrice() {
        double totalPrice = medicationBasket.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
        totalPriceField.setText(String.format("%.2f", totalPrice));
    }

    /**
     * Récupère les informations du formulaire pour créer ou mettre à jour un achat.
     *
     * @return Un objet Purchase avec les données du formulaire.
     */
    public Purchase getPurchase() {
        Customer selectedCustomer = customerComboBox.getValue();
        Doctor prescribingDoctor = prescribingDoctorComboBox.getValue();
        LocalDate prescriptionDate = prescriptionDatePicker.getValue();

        return new Purchase(
                selectedCustomer,
                LocalDate.now(),
                medicationBasket,
                prescribingDoctor,
                prescriptionDate
        );
    }

    /**
     * Initialise les données du formulaire avec les informations d'un achat existant.
     *
     * @param purchase L'achat à éditer ou à créer.
     */
    public void setPurchase(Purchase purchase) {

        if (purchase != null) {
            customerComboBox.setValue(purchase.getCustomer());
            medicationBasket.putAll(purchase.getMedicationBasket());
            updateMedicationBasketArea(purchase.getMedicationBasket());
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

    /**
     * Valide les données du formulaire avant la soumission.
     *
     * @return true si les données sont valides, sinon false.
     */
    public boolean validateInputs() {
        clearErrorLabels();

        // Validation de la quantité avec ValidationUtil
        String quantityError = ValidationUtil.validateQuantity(quantityField.getText());
        boolean isQuantityValid = quantityError == null;
        if (!isQuantityValid) {
            quantityErrorLabel.setText(quantityError);
        }

        // Validation du panier de médicaments : le panier ne doit pas être vide
        String basketError = ValidationUtil.validateBasket(medicationBasket);
        boolean isBasketValid = basketError == null;
        if (!isBasketValid) {
            medicationErrorLabel.setText(basketError);
        }


        // Validation des champs supplémentaires si "Avec ordonnance" est sélectionné
        boolean isPrescriptionValid = true;
        if ("Avec ordonnance".equals(purchaseTypeComboBox.getValue())) {
            // Validation du client avec ValidationUtil
            String customerError = ValidationUtil.validateCustomer(customerComboBox.getValue());
            boolean isCustomerValid = customerError == null;
            if (!isCustomerValid) {
                customerErrorLabel.setText(customerError);
                isPrescriptionValid = false;
            }

            // Validation du médecin avec ValidationUtil
            String doctorError = ValidationUtil.validateDoctor(prescribingDoctorComboBox.getValue());
            boolean isDoctorValid = doctorError == null;
            if (!isDoctorValid) {
                prescribingDoctorErrorLabel.setText(doctorError);
                isPrescriptionValid = false;
            }

            // Validation de la date de prescription avec ValidationUtil
            String prescriptionDateError = ValidationUtil.validatePrescriptionDate(prescriptionDatePicker.getValue());
            if (prescriptionDateError != null) {
                prescriptionDateErrorLabel.setText(prescriptionDateError);
                isPrescriptionValid = false;
            }
        }

        // Retourner true si toutes les validations sont passées, sinon false
        return !isBasketValid || !isPrescriptionValid;
    }

    /**
     * Réinitialise les labels d'erreurs associés aux champs de saisie.
     */
    private void clearErrorLabels() {
        medicationErrorLabel.setText("");
        customerErrorLabel.setText("");
        prescribingDoctorErrorLabel.setText("");
        prescriptionDateErrorLabel.setText("");
        quantityErrorLabel.setText("");
    }
}
