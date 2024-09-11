package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.Prescription;
import com.yb.sparadrap.store.PrescriptionDataStore;
import com.yb.sparadrap.util.EntityDialogUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * Contrôleur pour la gestion des ordonnances dans l'application.
 * Gère uniquement la visualisation des ordonnances dans le TableView.
 */
public class PrescriptionController {

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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        dateColumn.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getPrescriptionDate();
            return new SimpleStringProperty(date != null ? date.format(dateFormatter) : "");
        });

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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        FilteredList<Prescription> filteredData = new FilteredList<>(PrescriptionDataStore.getInstance().getPrescriptions(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(prescription -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Affiche toutes les ordonnances si le champ de recherche est vide
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Formater la date de prescription
                String formattedDate = prescription.getPrescriptionDate() != null
                        ? prescription.getPrescriptionDate().format(dateFormatter)
                        : "";

                // Rechercher dans le docteur, le patient, les médicaments, le spécialiste et la date formatée
                return formattedDate.contains(lowerCaseFilter)
                        || (prescription.getPatient() != null && prescription.getPatient().toString().toLowerCase().contains(lowerCaseFilter))
                        || (prescription.getDoctor() != null && prescription.getDoctor().toString().toLowerCase().contains(lowerCaseFilter))
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
     * Configure le bouton d'action pour visualiser l'ordonnance dans chaque ligne du TableView.
     */
    private void initializeActionsColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            final Button viewButton = new Button();

            {
                viewButton.setGraphic(new FontIcon(FontAwesomeSolid.EYE));
                viewButton.getStyleClass().add("button-info");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Prescription prescription = getTableView().getItems().get(getIndex());
                    viewButton.setOnAction(event -> handleViewPrescription(prescription));

                    HBox hbox = new HBox(viewButton);
                    hbox.setStyle("-fx-alignment: center;");
                    setGraphic(hbox);
                }
            }
        });
    }


    /**
     * Gère l'affichage des détails de l'ordonnance sélectionnée.
     *
     * @param prescription L'ordonnance à afficher.
     */
    private void handleViewPrescription(Prescription prescription) {
        // Ouvre une boîte de dialogue pour visualiser l'ordonnance
        EntityDialogUtil.openEntityDisplayDialog("/fxml/layout/PrescriptionDetails.fxml", "Détails de l'ordonnance",
                controller -> {
                    PrescriptionDetailsController prescriptionDetailsController = (PrescriptionDetailsController) controller;
                    prescriptionDetailsController.setPrescription(prescription);
                });
    }
}
