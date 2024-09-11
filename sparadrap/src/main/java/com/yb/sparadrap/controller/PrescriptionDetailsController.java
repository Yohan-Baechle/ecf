package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.Prescription;
import com.yb.sparadrap.model.enums.Specialty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * Contrôleur pour afficher les détails d'une ordonnance.
 * Ce contrôleur ne permet que l'affichage en lecture seule.
 */
public class PrescriptionDetailsController {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML
    private TextField prescriptionDateField;
    @FXML
    private TextField patientField;
    @FXML
    private TextField doctorField;
    @FXML
    private TextArea medicationsArea;
    @FXML
    private TextField specialtyField;

    /**
     * Initialise les détails de l'ordonnance en fonction de l'objet Prescription.
     *
     * @param prescription L'ordonnance à afficher.
     */
    public void setPrescription(Prescription prescription) {
        if (prescription != null) {
            // Met à jour la date de prescription au format dd/MM/yyyy
            if (prescription.getPrescriptionDate() != null) {
                prescriptionDateField.setText(prescription.getPrescriptionDate().format(dateFormatter));
            } else {
                prescriptionDateField.setText("Non spécifiée");
            }

            // Met à jour le nom du patient
            if (prescription.getPatient() != null) {
                patientField.setText(prescription.getPatient().getFirstName() + " " + prescription.getPatient().getLastName());
            } else {
                patientField.setText("Non spécifié");
            }

            // Met à jour le nom du médecin
            if (prescription.getDoctor() != null) {
                doctorField.setText(prescription.getDoctor().getFirstName() + " " + prescription.getDoctor().getLastName());
            } else {
                doctorField.setText("Non spécifié");
            }

            // Met à jour la liste des médicaments
            if (prescription.getMedications() != null && !prescription.getMedications().isEmpty()) {
                medicationsArea.setText(prescription.getMedications().stream()
                        .map(Medication::getName)
                        .collect(Collectors.joining(", ")));
            } else {
                medicationsArea.setText("Aucun médicament");
            }

            // Met à jour la spécialité : afficher "GÉNÉRALE" par défaut si le médecin n'est pas un spécialiste
            if (prescription.getSpecialist() != null) {
                specialtyField.setText(prescription.getSpecialist().getSpecialty().getDisplayName());
            } else {
                // Si ce n'est pas un spécialiste, définir la spécialité comme "GÉNÉRALE"
                specialtyField.setText(Specialty.GENERALISTE.getDisplayName());
            }
        }
    }
}