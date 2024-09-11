package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.Prescription;
import com.yb.sparadrap.model.store.PrescriptionDataStore;
import com.yb.sparadrap.util.AlertUtil;
import com.yb.sparadrap.util.ActionButtonUtil;
import com.yb.sparadrap.util.DeleteUtil;
import com.yb.sparadrap.util.EntityDialogUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * Contrôleur pour la gestion des ordonnances dans l'application.
 * Gère les interactions avec la liste des prescripteurs et les actions d'ajout, d'édition et de suppression.
 */
public class PrescriptionController {

    @FXML
    private Button addPrescriptionBtn;
    @FXML
    private TableView<Prescription> prescriptionTable;
    @FXML
    private TableColumn<Prescription, String> dateColumn;
    @FXML
    private TableColumn<Prescription, String> doctorColumn;
    @FXML
    private TableColumn<Prescription, String> patientColumn;
    @FXML
    private TableColumn<Prescription, String> medicationsColumn;
    @FXML
    private TableColumn<Prescription, String> specialistColumn;
    @FXML
    private TableColumn<Prescription, Void> actionColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label statusLabel;

    /**
     * Initialise le contrôleur : configuration des colonnes, des données ordonnances,
     * de la barre de recherche et des boutons d'action.
     */
    @FXML
    public void initialize() {
        initializeColumns();
        initializePrescriptionData();
        initializeSearchField();
        initializeActionsColumn();

        prescriptionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_NEXT_COLUMN);
    }

    /**
     * Configure les colonnes du TableView avec les propriétés observables des ordonnances.
     */
    private void initializeColumns() {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().prescriptionDateProperty().asString());
        doctorColumn.setCellValueFactory(cellData -> cellData.getValue().doctorProperty().asString());
        patientColumn.setCellValueFactory(cellData -> cellData.getValue().patientProperty().asString());
        medicationsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getMedications().stream()
                        .map(Medication::getName)
                        .collect(Collectors.joining(", "))
        ));
        specialistColumn.setCellValueFactory(cellData -> cellData.getValue().specialistProperty().isNull().get() ?
                new SimpleStringProperty("Aucun") :
                new SimpleStringProperty(cellData.getValue().getSpecialist().getSpecialty().getDisplayName()));

        // Configuration des largeurs des colonnes
        dateColumn.prefWidthProperty().bind(prescriptionTable.widthProperty().multiply(0.10));
        doctorColumn.prefWidthProperty().bind(prescriptionTable.widthProperty().multiply(0.10));
        patientColumn.prefWidthProperty().bind(prescriptionTable.widthProperty().multiply(0.20));
        medicationsColumn.prefWidthProperty().bind(prescriptionTable.widthProperty().multiply(0.40));
        specialistColumn.prefWidthProperty().bind(prescriptionTable.widthProperty().multiply(0.10));
        actionColumn.prefWidthProperty().bind(prescriptionTable.widthProperty().multiply(0.10));
    }

    /**
     * Initialise les données ordonnances à partir de PrescriptionDataStore.
     */
    private void initializePrescriptionData() {
        prescriptionTable.setItems(PrescriptionDataStore.getInstance().getPrescriptions());
    }

    /**
     * Initialise la barre de recherche pour filtrer les ordonnances dans le TableView.
     */
    private void initializeSearchField() {
        FilteredList<Prescription> filteredData = new FilteredList<>(PrescriptionDataStore.getInstance().getPrescriptions(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(prescription -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Affiche toutes les ordonnances si le champ de recherche est vide
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Rechercher dans le docteur, le patient, les médicaments, le spécialiste et la date
                return prescription.getPrescriptionDate().toString().toLowerCase().contains(lowerCaseFilter)
                        || prescription.getPatient().toString().toLowerCase().contains(lowerCaseFilter)
                        || prescription.getDoctor().toString().toLowerCase().contains(lowerCaseFilter)
                        || prescription.getMedications().stream()
                        .map(med -> med.getName().toLowerCase())
                        .collect(Collectors.joining(", "))
                        .contains(lowerCaseFilter)
                        || (prescription.getSpecialist() != null && prescription.getSpecialist().toString().toLowerCase().contains(lowerCaseFilter));
            });
        });

        SortedList<Prescription> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(prescriptionTable.comparatorProperty());
        prescriptionTable.setItems(sortedData);
    }


    /**
     * Configure les boutons d'action (édition et suppression) pour chaque ligne du TableView.
     */
    private void initializeActionsColumn() {
        // Capturer une référence explicite au contrôleur parent
        PrescriptionController controller = this;

        actionColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null); // Si la cellule est vide, ne rien afficher
                } else {
                    Prescription prescription = getTableView().getItems().get(getIndex());
                    // Utiliser la référence explicite au contrôleur parent pour accéder aux méthodes
                    setGraphic(ActionButtonUtil.createEditDeleteButtons(prescription,
                            controller::handleEditPrescription,
                            controller::handleDeletePrescription));
                }
            }
        });
    }

    /**
     * Gère l'ajout d'une nouvelle ordonnance à l'aide d'une boîte de dialogue.
     */
    @FXML
    private void handleAddPrescription() {
        Prescription newPrescription = new Prescription(LocalDate.now(), null, null, FXCollections.observableArrayList(), null);
        openPrescriptionForm(newPrescription, "Ajouter une nouvelle ordonnance");
    }

    /**
     * Gère la modification d'une ordonnance existante.
     *
     * @param prescription L'ordonnance à modifier.
     */
    @FXML
    private void handleEditPrescription(Prescription prescription) {
        openPrescriptionForm(prescription, "Modification ordonnance");
    }


    /**
     * Ouvre un formulaire d'ordonnance pour ajouter ou éditer une ordonnance.
     *
     * @param prescription    Le prescripteur à ajouter ou modifier.
     * @param dialogTitle Le titre de la boîte de dialogue.
     */
    private void openPrescriptionForm(Prescription prescription, String dialogTitle) {
        EntityDialogUtil.openEntityFormDialog("/fxml/layout/PrescriptionForm.fxml", dialogTitle,
                controller -> {
                    PrescriptionFormController prescriptionFormController = (PrescriptionFormController) controller;
                    prescriptionFormController.setPrescription(prescription);
                },
                controller -> ((PrescriptionFormController) controller).getPrescription()
        ).ifPresent(updatedPrescription -> {
            if (prescriptionTable.getItems().contains(prescription)) {
                int index = PrescriptionDataStore.getInstance().getPrescriptions().indexOf(prescription);
                PrescriptionDataStore.getInstance().getPrescriptions().set(index, updatedPrescription);
                AlertUtil.updateStatusLabel(statusLabel, "L'ordonnance a été modifiée avec succès.", "success");
            } else {
                PrescriptionDataStore.getInstance().addPrescription(updatedPrescription);
                AlertUtil.updateStatusLabel(statusLabel, "L'ordonannce' a été créés avec succès.", "success");
            }
        });
    }

    /**
     * Gère la suppression d'une ordonnance avec confirmation.
     *
     * @param prescription L'ordonnance à supprimer.
     */
    private void handleDeletePrescription(Prescription prescription) {
        DeleteUtil.handleDelete(prescription,
                PrescriptionDataStore.getInstance()::removePrescription,
                "L'ordonnance a été supprimée avec succès.",
                statusLabel);
    }
}
