package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.*;
import com.yb.sparadrap.model.enums.Specialty;
import com.yb.sparadrap.model.store.CustomerDataStore;
import com.yb.sparadrap.model.store.DoctorDataStore;
import com.yb.sparadrap.model.store.MedicationDataStore;
import com.yb.sparadrap.util.ValidationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PrescriptionFormController {

    @FXML
    private DatePicker prescriptionDatePicker;
    @FXML
    private ComboBox<Customer> patientComboBox;
    @FXML
    private ComboBox<Doctor> doctorComboBox;
    @FXML
    private TextArea medicationsField;
    @FXML
    private ComboBox<Specialty> specialtyComboBox; // ComboBox pour la spécialité
    @FXML
    private Label prescriptionDateErrorLabel;
    @FXML
    private Label patientNameErrorLabel;
    @FXML
    private Label doctorNameErrorLabel;
    @FXML
    private Label medicationsErrorLabel;
    @FXML
    private Label specialtyErrorLabel;

    private Map<Control, Label> fieldErrorMap; // Utiliser Control au lieu de TextField

    @FXML
    public void initialize() {
        // Remplir les ComboBoxes pour les patients, les médecins et les spécialités
        patientComboBox.setItems(CustomerDataStore.getInstance().getCustomers());
        doctorComboBox.setItems(DoctorDataStore.getInstance().getDoctors());

        // Remplir la ComboBox des spécialités avec l'énumération Specialty
        specialtyComboBox.getItems().setAll(Specialty.values());

        // Mapping des champs de saisie avec leurs labels d'erreur
        fieldErrorMap = new HashMap<>();
        fieldErrorMap.put(patientComboBox, patientNameErrorLabel);
        fieldErrorMap.put(doctorComboBox, doctorNameErrorLabel);
        fieldErrorMap.put(medicationsField, medicationsErrorLabel);
        fieldErrorMap.put(specialtyComboBox, specialtyErrorLabel);
    }

    public void setPrescription(Prescription prescription) {
        if (prescription != null) {
            // Met à jour le DatePicker avec la date de prescription
            prescriptionDatePicker.setValue(prescription.getPrescriptionDate());

            // Met à jour les ComboBoxes pour le patient et le médecin
            patientComboBox.setValue(prescription.getPatient());
            doctorComboBox.setValue(prescription.getDoctor());

            // Met à jour la TextArea avec la liste des médicaments
            medicationsField.setText(prescription.getMedications().stream()
                    .map(Medication::getName)
                    .collect(Collectors.joining(", ")));

            // Met à jour la ComboBox pour la spécialité
            if (prescription.getSpecialist() != null) {
                specialtyComboBox.setValue(prescription.getSpecialist().getSpecialty());
            } else {
                specialtyComboBox.setValue(null);
            }
        }
    }

    public Prescription getPrescription() {
        // Récupérer les valeurs des champs du formulaire
        LocalDate prescriptionDate = prescriptionDatePicker.getValue();
        Customer patient = patientComboBox.getValue();
        Doctor doctor = doctorComboBox.getValue();
        List<Medication> medicationsList = Arrays.stream(medicationsField.getText().split(","))
                .map(String::trim)
                .map(medicationName -> MedicationDataStore.getInstance().getMedications().stream()
                        .filter(medication -> medication.getName().equalsIgnoreCase(medicationName))
                        .findFirst().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Specialty specialty = specialtyComboBox.getValue();
        Specialist specialist = null;
        if (specialty != null && doctor != null) {
            specialist = new Specialist(doctor.getFirstName(), doctor.getLastName(), doctor.getAddress(), doctor.getPhoneNumber(), doctor.getEmail(), doctor.getRegistrationNumber(), specialty);
        }

        // Créer et retourner un objet Prescription avec les données du formulaire
        return new Prescription(prescriptionDate, doctor, patient, medicationsList, specialist);
    }

    public boolean validateInputs() {
        clearErrorLabels();

        // Validation de la date de prescription
        String prescriptionDateError = ValidationUtil.validatePrescriptionDate(prescriptionDatePicker.getValue());
        boolean isPrescriptionDateValid = prescriptionDateError == null;
        if (!isPrescriptionDateValid) {
            prescriptionDateErrorLabel.setText(prescriptionDateError);
        }

        // Validation du patient
        boolean isPatientValid = patientComboBox.getValue() != null;
        if (!isPatientValid) {
            patientNameErrorLabel.setText("Veuillez sélectionner un patient.");
        }

        // Validation du médecin
        boolean isDoctorValid = doctorComboBox.getValue() != null;
        if (!isDoctorValid) {
            doctorNameErrorLabel.setText("Veuillez sélectionner un médecin.");
        }

        // Validation de la liste des médicaments
        String medicationsError = ValidationUtil.validateMedicationsList(medicationsField.getText().trim());
        boolean isMedicationsListValid = medicationsError == null;
        if (!isMedicationsListValid) {
            medicationsErrorLabel.setText(medicationsError);
        }

        // Validation de la spécialité
        boolean isSpecialtyValid = specialtyComboBox.getValue() != null;
        if (!isSpecialtyValid) {
            specialtyErrorLabel.setText("La spécialité est obligatoire.");
        }

        return !isPrescriptionDateValid || !isPatientValid || !isDoctorValid || !isMedicationsListValid || !isSpecialtyValid;
    }

    private void clearErrorLabels() {
        // Réinitialiser tous les labels d'erreur associés aux champs de saisie
        fieldErrorMap.values().forEach(label -> label.setText(""));
    }
}
