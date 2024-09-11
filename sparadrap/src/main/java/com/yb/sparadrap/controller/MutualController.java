package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Mutual;
import com.yb.sparadrap.model.enums.Department;
import com.yb.sparadrap.store.MutualDataStore;
import com.yb.sparadrap.util.ActionButtonUtil;
import com.yb.sparadrap.util.AlertUtil;
import com.yb.sparadrap.util.DeleteUtil;
import com.yb.sparadrap.util.EntityDialogUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Contrôleur pour la gestion des mutuelles dans l'application.
 * Gère les interactions avec la liste des mutuelles et les actions d'ajout, d'édition et de suppression.
 */
public class MutualController {

    @FXML
    private Button addMutualBtn;
    @FXML
    private TableView<Mutual> mutualTable;
    @FXML
    private TableColumn<Mutual, String> nameColumn;
    @FXML
    private TableColumn<Mutual, String> addressColumn;
    @FXML
    private TableColumn<Mutual, String> phoneNumberColumn;
    @FXML
    private TableColumn<Mutual, String> emailColumn;
    @FXML
    private TableColumn<Mutual, String> departmentColumn;
    @FXML
    private TableColumn<Mutual, String> reimbursementRateColumn;
    @FXML
    private TableColumn<Mutual, Void> actionColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label statusLabel;

    /**
     * Initialise le contrôleur : configuration des colonnes, des données mutuelles,
     * de la barre de recherche et des boutons d'action.
     */
    @FXML
    public void initialize() {
        initializeColumns();
        initializeMutualData();
        initializeSearchField();
        initializeActionsColumn();

        mutualTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_NEXT_COLUMN);
    }

    /**
     * Configure les colonnes du TableView avec les propriétés observables des mutuelles.
     */
    private void initializeColumns() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() -> cellData.getValue().getAddress().toString(),
                        cellData.getValue().getAddress().streetProperty(),
                        cellData.getValue().getAddress().postalCodeProperty(),
                        cellData.getValue().getAddress().cityProperty())
        );
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        departmentColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDepartment().getFormattedName()));
        reimbursementRateColumn.setCellValueFactory(cellData -> cellData.getValue().reimbursementRateProperty().asObject().asString());


        // Configuration des largeurs des colonnes
        nameColumn.prefWidthProperty().bind(mutualTable.widthProperty().multiply(0.20));
        addressColumn.prefWidthProperty().bind(mutualTable.widthProperty().multiply(0.20));
        departmentColumn.prefWidthProperty().bind(mutualTable.widthProperty().multiply(0.15));
        phoneNumberColumn.prefWidthProperty().bind(mutualTable.widthProperty().multiply(0.10));
        emailColumn.prefWidthProperty().bind(mutualTable.widthProperty().multiply(0.20));
        reimbursementRateColumn.prefWidthProperty().bind(mutualTable.widthProperty().multiply(0.05));
        actionColumn.prefWidthProperty().bind(mutualTable.widthProperty().multiply(0.10));
    }

    /**
     * Initialise les données mutuelles à partir de MutualDataStore.
     */
    private void initializeMutualData() {
        mutualTable.setItems(MutualDataStore.getInstance().getMutuals());
    }

    /**
     * Initialise la barre de recherche pour filtrer les mutuelles dans le TableView.
     */
    private void initializeSearchField() {
        FilteredList<Mutual> filteredData = new FilteredList<>(MutualDataStore.getInstance().getMutuals(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(mutual -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Affiche toutes les mutuelles si le champ de recherche est vide
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return mutual.getName().toLowerCase().contains(lowerCaseFilter)
                        || mutual.getAddress().toString().toLowerCase().contains(lowerCaseFilter)
                        || mutual.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)
                        || mutual.getEmail().toLowerCase().contains(lowerCaseFilter)
                        || Double.toString(mutual.getReimbursementRate()).toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Mutual> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(mutualTable.comparatorProperty());
        mutualTable.setItems(sortedData);
    }

    /**
     * Configure les boutons d'action (édition et suppression) pour chaque ligne du TableView.
     */
    private void initializeActionsColumn() {
        // Capturer une référence explicite au contrôleur parent
        MutualController controller = this;

        actionColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null); // Si la cellule est vide, ne rien afficher
                } else {
                    Mutual mutual = getTableView().getItems().get(getIndex());
                    // Utiliser la référence explicite au contrôleur parent pour accéder aux méthodes
                    setGraphic(ActionButtonUtil.createEditDeleteButtons(mutual,
                            controller::handleEditMutual,
                            controller::handleDeleteMutual));
                }
            }
        });
    }

    /**
     * Gère l'ajout d'une nouvelle mutuelle à l'aide d'une boîte de dialogue.
     */
    @FXML
    private void handleAddMutual() {
        Mutual newMutual = new Mutual("", new Address("", "", ""), Department.PARIS, "", "", 0.0);
        openMutualForm(newMutual, "Ajouter une nouvelle mutuelle");
    }

    /**
     * Gère la modification d'une mutuelle existante.
     *
     * @param mutual La mutuelle à modifier.
     */
    @FXML
    private void handleEditMutual(Mutual mutual) {
        openMutualForm(mutual, "Modification mutuelle");
    }


    /**
     * Ouvre un formulaire de mutuelle pour ajouter ou éditer une mutuelle.
     *
     * @param mutual      La mutuelle à ajouter ou modifier.
     * @param dialogTitle Le titre de la boîte de dialogue.
     */
    private void openMutualForm(Mutual mutual, String dialogTitle) {
        EntityDialogUtil.openEntityFormDialog("/fxml/layout/MutualForm.fxml", dialogTitle,
                controller -> {
                    MutualFormController mutualFormController = (MutualFormController) controller;
                    mutualFormController.setMutual(mutual);
                },
                controller -> ((MutualFormController) controller).getMutual()
        ).ifPresent(updatedMutual -> {
            if (mutualTable.getItems().contains(mutual)) {
                int index = MutualDataStore.getInstance().getMutuals().indexOf(mutual);
                MutualDataStore.getInstance().getMutuals().set(index, updatedMutual);
                AlertUtil.updateStatusLabel(statusLabel, "La mutuelle a été modifié avec succès.", "success");
            } else {
                MutualDataStore.getInstance().addMutual(updatedMutual);
                AlertUtil.updateStatusLabel(statusLabel, "La mutuelle a été créé avec succès.", "success");
            }
        });
    }

    /**
     * Gère la suppression d'une mutuelle avec confirmation.
     *
     * @param mutual La mutuelle à supprimer.
     */
    private void handleDeleteMutual(Mutual mutual) {
        DeleteUtil.handleDelete(mutual,
                MutualDataStore.getInstance()::removeMutual,
                "La mutuelle a été supprimée avec succès.",
                statusLabel);
    }
}
