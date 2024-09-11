package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.store.MedicationDataStore;
import com.yb.sparadrap.util.ActionButtonUtil;
import com.yb.sparadrap.util.AlertUtil;
import com.yb.sparadrap.util.DeleteUtil;
import com.yb.sparadrap.util.EntityDialogUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Contrôleur pour la gestion des médicaments dans l'application.
 * Gère les interactions avec la liste des médicaments et les actions d'ajout, d'édition et de suppression.
 */
public class MedicationController {

    @FXML
    private Button addMedicationBtn;
    @FXML
    private TableView<Medication> medicationTable;
    @FXML
    private TableColumn<Medication, String> nameColumn;
    @FXML
    private TableColumn<Medication, String> categoryColumn;
    @FXML
    private TableColumn<Medication, String> launchDateColumn;
    @FXML
    private TableColumn<Medication, String> quantityColumn;
    @FXML
    private TableColumn<Medication, String> priceColumn;
    @FXML
    private TableColumn<Medication, Void> actionColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label statusLabel;

    /**
     * Initialise le contrôleur : configuration des colonnes, des données des médicaments,
     * de la barre de recherche et des boutons d'action.
     */
    @FXML
    public void initialize() {
        initializeColumns();
        initializeMedicationData();
        initializeSearchField();
        initializeActionsColumn();

        medicationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_NEXT_COLUMN);
    }

    /**
     * Configure les colonnes du TableView avec les propriétés observables des médicaments.
     */
    private void initializeColumns() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        categoryColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getCategory().getDisplayName());
        });
        launchDateColumn.setCellValueFactory(cellData -> cellData.getValue().launchDateProperty().asString());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject().asString());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject().asString());

        // Configuration des largeurs des colonnes
        nameColumn.prefWidthProperty().bind(medicationTable.widthProperty().multiply(0.10));
        categoryColumn.prefWidthProperty().bind(medicationTable.widthProperty().multiply(0.10));
        launchDateColumn.prefWidthProperty().bind(medicationTable.widthProperty().multiply(0.20));
        priceColumn.prefWidthProperty().bind(medicationTable.widthProperty().multiply(0.20));
        quantityColumn.prefWidthProperty().bind(medicationTable.widthProperty().multiply(0.10));
        priceColumn.prefWidthProperty().bind(medicationTable.widthProperty().multiply(0.20));
        actionColumn.prefWidthProperty().bind(medicationTable.widthProperty().multiply(0.10));
    }

    /**
     * Initialise les données des médicaments à partir de MedicationDataStore.
     */
    private void initializeMedicationData() {
        medicationTable.setItems(MedicationDataStore.getInstance().getMedications());
    }

    /**
     * Initialise la barre de recherche pour filtrer les médicaments dans le TableView.
     */
    private void initializeSearchField() {
        FilteredList<Medication> filteredData = new FilteredList<>(MedicationDataStore.getInstance().getMedications(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(medication -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Affiche tous les médicaments si le champ de recherche est vide
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return medication.getName().toLowerCase().contains(lowerCaseFilter)
                        || medication.getCategory().toString().toLowerCase().contains(lowerCaseFilter)
                        || Double.toString(medication.getPrice()).toLowerCase().contains(lowerCaseFilter)
                        || Integer.toString(medication.getQuantity()).toLowerCase().contains(lowerCaseFilter)
                        || medication.getLaunchDate().toString().toLowerCase().contains(lowerCaseFilter);
            });
        });


        SortedList<Medication> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(medicationTable.comparatorProperty());
        medicationTable.setItems(sortedData);
    }

    /**
     * Configure les boutons d'action (édition et suppression) pour chaque ligne du TableView.
     */
    private void initializeActionsColumn() {
        // Capturer une référence explicite au contrôleur parent
        MedicationController controller = this;

        actionColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null); // Si la cellule est vide, ne rien afficher
                } else {
                    Medication medication = getTableView().getItems().get(getIndex());
                    // Utiliser la référence explicite au contrôleur parent pour accéder aux méthodes
                    setGraphic(ActionButtonUtil.createEditDeleteButtons(medication,
                            controller::handleEditMedication,
                            controller::handleDeleteMedication));
                }
            }
        });
    }

    /**
     * Gère l'ajout d'un nouveau médicament à l'aide d'une boîte de dialogue.
     */
    @FXML
    private void handleAddMedication() {
        Medication newMedication = new Medication("", null, null, 0, 0.0);
        openMedicationForm(newMedication, "Ajouter un nouveau médicament");
    }

    /**
     * Gère la modification d'un médicament existant.
     *
     * @param medication Le médicament à modifier.
     */
    @FXML
    private void handleEditMedication(Medication medication) {
        openMedicationForm(medication, "Modification médicament");
    }


    /**
     * Ouvre un formulaire de médicament pour ajouter ou éditer un médicament.
     *
     * @param medication  Le médicament à ajouter ou modifier.
     * @param dialogTitle Le titre de la boîte de dialogue.
     */
    private void openMedicationForm(Medication medication, String dialogTitle) {
        EntityDialogUtil.openEntityFormDialog("/fxml/layout/MedicationForm.fxml", dialogTitle,
                controller -> {
                    MedicationFormController medicationFormController = (MedicationFormController) controller;
                    medicationFormController.setMedication(medication);
                },
                controller -> ((MedicationFormController) controller).getMedications()
        ).ifPresent(updatedMedication -> {
            if (medicationTable.getItems().contains(medication)) {
                int index = MedicationDataStore.getInstance().getMedications().indexOf(medication);
                MedicationDataStore.getInstance().getMedications().set(index, updatedMedication);
                AlertUtil.updateStatusLabel(statusLabel, "Le médicament a été modifié avec succès.", "success");
            } else {
                MedicationDataStore.getInstance().addMedication(updatedMedication);
                AlertUtil.updateStatusLabel(statusLabel, "Le médicament a été créé avec succès.", "success");
            }
        });
    }

    /**
     * Gère la suppression d'un médicament avec confirmation.
     *
     * @param medication Le médicament à supprimer.
     */
    private void handleDeleteMedication(Medication medication) {
        DeleteUtil.handleDelete(medication,
                MedicationDataStore.getInstance()::removeMedication,
                "Le médicament a été supprimé avec succès.",
                statusLabel);
    }
}
