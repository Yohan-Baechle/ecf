package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.store.CustomerDataStore;
import com.yb.sparadrap.util.ActionButtonUtil;
import com.yb.sparadrap.util.AlertUtil;
import com.yb.sparadrap.util.DeleteUtil;
import com.yb.sparadrap.util.EntityDialogUtil;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Contrôleur pour la gestion des clients dans l'application.
 * Gère les interactions avec la liste des clients et les actions d'ajout, d'édition et de suppression.
 */
public class CustomerController {

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> lastNameColumn;
    @FXML
    private TableColumn<Customer, String> firstNameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> phoneNumberColumn;
    @FXML
    private TableColumn<Customer, String> emailColumn;
    @FXML
    private TableColumn<Customer, String> socialSecurityNumberColumn;
    @FXML
    private TableColumn<Customer, String> birthDateColumn;
    @FXML
    private TableColumn<Customer, Void> actionColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label statusLabel;

    /**
     * Initialise le contrôleur : configuration des colonnes, des données clients,
     * de la barre de recherche et des boutons d'action.
     */
    @FXML
    public void initialize() {
        initializeColumns();
        initializeCustomerData();
        initializeSearchField();
        initializeActionsColumn();
    }

    /**
     * Configure les colonnes du TableView avec les propriétés observables des clients.
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
        socialSecurityNumberColumn.setCellValueFactory(cellData -> cellData.getValue().socialSecurityNumberProperty());
        birthDateColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                                cellData.getValue().getBirthDate() != null ?
                                        cellData.getValue().getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "",
                        cellData.getValue().birthDateProperty())
        );

        // Configuration des largeurs des colonnes
        firstNameColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.10));
        lastNameColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.10));
        addressColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.20));
        emailColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.20));
        phoneNumberColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.10));
        socialSecurityNumberColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.20));
        actionColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.10));
    }

    /**
     * Initialise les données clients à partir de CustomerDataStore.
     */
    private void initializeCustomerData() {
        customerTable.setItems(CustomerDataStore.getInstance().getCustomers());
    }

    /**
     * Initialise la barre de recherche pour filtrer les clients dans le TableView.
     */
    private void initializeSearchField() {
        FilteredList<Customer> filteredData = new FilteredList<>(CustomerDataStore.getInstance().getCustomers(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Affiche tous les clients si le champ de recherche est vide
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return customer.getFirstName().toLowerCase().contains(lowerCaseFilter)
                        || customer.getLastName().toLowerCase().contains(lowerCaseFilter)
                        || customer.getEmail().toLowerCase().contains(lowerCaseFilter)
                        || customer.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)
                        || customer.getAddress().toString().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortedData);
    }

    /**
     * Configure les boutons d'action (édition et suppression) pour chaque ligne du TableView.
     */
    private void initializeActionsColumn() {
        // Capturer une référence explicite au contrôleur parent
        CustomerController controller = this;

        actionColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null); // Si la cellule est vide, ne rien afficher
                } else {
                    Customer customer = getTableView().getItems().get(getIndex());
                    // Utiliser la référence explicite au contrôleur parent pour accéder aux méthodes
                    setGraphic(ActionButtonUtil.createEditDeleteButtons(customer,
                            controller::handleEditCustomer,
                            controller::handleDeleteCustomer));
                }
            }
        });
    }

    /**
     * Gère l'ajout d'un nouveau client à l'aide d'une boîte de dialogue.
     */
    @FXML
    private void handleAddCustomer() {
        Customer newCustomer = new Customer("", "", new Address("", "", ""), "", "", "", LocalDate.now(), null, null);
        openCustomerForm(newCustomer, "Ajouter un nouveau client");
    }

    /**
     * Gère la modification d'un client existant.
     *
     * @param customer Le client à modifier.
     */
    @FXML
    private void handleEditCustomer(Customer customer) {
        openCustomerForm(customer, "Modification client");
    }


    /**
     * Ouvre un formulaire de client pour ajouter ou éditer un client.
     *
     * @param customer    Le client à ajouter ou modifier.
     * @param dialogTitle Le titre de la boîte de dialogue.
     */
    private void openCustomerForm(Customer customer, String dialogTitle) {
        EntityDialogUtil.openEntityFormDialog("/fxml/layout/CustomerForm.fxml", dialogTitle,
                controller -> {
                    CustomerFormController customerFormController = (CustomerFormController) controller;
                    customerFormController.setCustomer(customer);
                },
                controller -> ((CustomerFormController) controller).getCustomer()
        ).ifPresent(updatedCustomer -> {
            if (customerTable.getItems().contains(customer)) {
                int index = CustomerDataStore.getInstance().getCustomers().indexOf(customer);
                CustomerDataStore.getInstance().getCustomers().set(index, updatedCustomer);
                AlertUtil.updateStatusLabel(statusLabel, "Le client a été modifié avec succès.", "success");
            } else {
                CustomerDataStore.getInstance().addCustomer(updatedCustomer);
                AlertUtil.updateStatusLabel(statusLabel, "Le client a été créé avec succès.", "success");
            }
        });
    }

    /**
     * Gère la suppression d'un client avec confirmation.
     *
     * @param customer Le client à supprimer.
     */
    private void handleDeleteCustomer(Customer customer) {
        DeleteUtil.handleDelete(customer,
                CustomerDataStore.getInstance()::removeCustomer,
                "Le client a été supprimé avec succès.",
                statusLabel);
    }
}
