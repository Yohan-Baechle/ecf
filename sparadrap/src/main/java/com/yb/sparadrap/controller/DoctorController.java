package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Doctor;
import com.yb.sparadrap.model.Specialist;
import com.yb.sparadrap.store.DoctorDataStore;
import com.yb.sparadrap.util.ActionButtonUtil;
import com.yb.sparadrap.util.AlertUtil;
import com.yb.sparadrap.util.DeleteUtil;
import com.yb.sparadrap.util.EntityDialogUtil;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Contrôleur pour la gestion des prescripteurs dans l'application.
 * Gère les interactions avec la liste des prescripteurs et les actions d'ajout, d'édition et de suppression.
 */
public class DoctorController {

    @FXML
    private TableView<Doctor> doctorTable;
    @FXML
    private TableColumn<Doctor, String> lastNameColumn;
    @FXML
    private TableColumn<Doctor, String> firstNameColumn;
    @FXML
    private TableColumn<Doctor, String> addressColumn;
    @FXML
    private TableColumn<Doctor, String> phoneNumberColumn;
    @FXML
    private TableColumn<Doctor, String> emailColumn;
    @FXML
    private TableColumn<Doctor, String> specialtyColumn;
    @FXML
    private TableColumn<Doctor, String> registrationNumberColumn;
    @FXML
    private TableColumn<Doctor, Void> actionColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label statusLabel;

    /**
     * Initialise le contrôleur : configuration des colonnes, des données prescripteurs,
     * de la barre de recherche et des boutons d'action.
     */
    @FXML
    public void initialize() {
        initializeColumns();
        initializeDoctorData();
        initializeSearchField();
        initializeActionsColumn();
    }

    /**
     * Configure les colonnes du TableView avec les propriétés observables des prescripteurs.
     */
    private void initializeColumns() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        addressColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() -> cellData.getValue().getAddress().toString(),
                        cellData.getValue().getAddress().streetProperty(),
                        cellData.getValue().getAddress().zipCodeProperty(),
                        cellData.getValue().getAddress().cityProperty())
        );
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        registrationNumberColumn.setCellValueFactory(cellData -> cellData.getValue().registrationNumberProperty());

        // Configuration de la colonne Spécialité pour afficher uniquement si c'est un Specialist
        specialtyColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Specialist specialist) {
                return Bindings.createStringBinding(() -> specialist.getSpecialty().getDisplayName());
            }
            return Bindings.createStringBinding(() -> "Générale"); // Si pas de spécialité, afficher "Générale"
        });

        // Configuration des largeurs des colonnes
        firstNameColumn.prefWidthProperty().bind(doctorTable.widthProperty().multiply(0.10));
        lastNameColumn.prefWidthProperty().bind(doctorTable.widthProperty().multiply(0.10));
        addressColumn.prefWidthProperty().bind(doctorTable.widthProperty().multiply(0.20));
        emailColumn.prefWidthProperty().bind(doctorTable.widthProperty().multiply(0.20));
        phoneNumberColumn.prefWidthProperty().bind(doctorTable.widthProperty().multiply(0.10));
        registrationNumberColumn.prefWidthProperty().bind(doctorTable.widthProperty().multiply(0.10));
        specialtyColumn.prefWidthProperty().bind(doctorTable.widthProperty().multiply(0.10));
        actionColumn.prefWidthProperty().bind(doctorTable.widthProperty().multiply(0.10));
    }

    /**
     * Initialise les données prescripteurs à partir de DoctorDataStore.
     */
    private void initializeDoctorData() {
        doctorTable.setItems(DoctorDataStore.getInstance().getDoctors());
    }

    /**
     * Initialise la barre de recherche pour filtrer les prescripteurs dans le TableView.
     */
    private void initializeSearchField() {
        FilteredList<Doctor> filteredData = new FilteredList<>(DoctorDataStore.getInstance().getDoctors(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(doctor -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Affiche tous les prescripteurs si le champ de recherche est vide
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return doctor.getFirstName().toLowerCase().contains(lowerCaseFilter)
                        || doctor.getLastName().toLowerCase().contains(lowerCaseFilter)
                        || doctor.getEmail().toLowerCase().contains(lowerCaseFilter)
                        || doctor.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)
                        || doctor.getAddress().toString().toLowerCase().contains(lowerCaseFilter)
                        || doctor.getRegistrationNumber().toLowerCase().contains(lowerCaseFilter)
                        || (doctor instanceof Specialist && ((Specialist) doctor).getSpecialty().getDisplayName().toLowerCase().contains(lowerCaseFilter));
            });
        });

        SortedList<Doctor> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(doctorTable.comparatorProperty());
        doctorTable.setItems(sortedData);
    }

    /**
     * Configure les boutons d'action (édition et suppression) pour chaque ligne du TableView.
     */
    private void initializeActionsColumn() {
        // Capturer une référence explicite au contrôleur parent
        DoctorController controller = this;

        actionColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null); // Si la cellule est vide, ne rien afficher
                } else {
                    Doctor doctor = getTableView().getItems().get(getIndex());
                    // Utiliser la référence explicite au contrôleur parent pour accéder aux méthodes
                    setGraphic(ActionButtonUtil.createEditDeleteButtons(doctor,
                            controller::handleEditDoctor,
                            controller::handleDeleteDoctor));
                }
            }
        });
    }

    /**
     * Gère l'ajout d'un nouveau prescripteur à l'aide d'une boîte de dialogue.
     */
    @FXML
    private void handleAddDoctor() {
        Doctor newDoctor = new Doctor("", "", new Address("", "", ""), "", "", "");
        openDoctorForm(newDoctor, "Ajouter un nouveau prescripteur");
    }

    /**
     * Gère la modification d'un prescripteur existant.
     *
     * @param doctor Le prescripteur à modifier.
     */
    @FXML
    private void handleEditDoctor(Doctor doctor) {
        openDoctorForm(doctor, "Modification prescripteur");
    }


    /**
     * Ouvre un formulaire de prescripteur pour ajouter ou éditer un prescripteur.
     *
     * @param doctor      Le prescripteur à ajouter ou modifier.
     * @param dialogTitle Le titre de la boîte de dialogue.
     */
    private void openDoctorForm(Doctor doctor, String dialogTitle) {
        EntityDialogUtil.openEntityFormDialog("/fxml/layout/DoctorForm.fxml", dialogTitle,
                controller -> {
                    DoctorFormController doctorFormController = (DoctorFormController) controller;
                    doctorFormController.setDoctor(doctor);
                },
                controller -> ((DoctorFormController) controller).getDoctor()
        ).ifPresent(updatedDoctor -> {
            if (doctorTable.getItems().contains(doctor)) {
                int index = DoctorDataStore.getInstance().getDoctors().indexOf(doctor);
                DoctorDataStore.getInstance().getDoctors().set(index, updatedDoctor);
                AlertUtil.updateStatusLabel(statusLabel, "Le prescripteur a été modifié avec succès.", "success");
            } else {
                DoctorDataStore.getInstance().addDoctor(updatedDoctor);
                AlertUtil.updateStatusLabel(statusLabel, "Le prescripteur a été créé avec succès.", "success");
            }
        });
    }

    /**
     * Gère la suppression d'un prescripteur avec confirmation.
     *
     * @param doctor Le prescripteur à supprimer.
     */
    private void handleDeleteDoctor(Doctor doctor) {
        DeleteUtil.handleDelete(doctor,
                DoctorDataStore.getInstance()::removeDoctor,
                "Le prescripteur a été supprimé avec succès.",
                statusLabel);
    }
}
