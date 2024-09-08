package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.store.CustomerDataStore;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller for managing customer in the application.
 * Handles interactions with the customer list and manages customer addition, editing, and deletion.
 */
public class CustomerController {

    @FXML
    private Button addCustomerBtn;
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
     * Initializes the controller by setting up columns, customer data, search functionality, and action buttons.
     */
    @FXML
    public void initialize() {
        initializeColumns();
        initializeCustomerData();
        initializeSearchField();
        initializeActionsColumn();
    }

    /**
     * Configures the columns of the TableView.
     * Sets up columns to use observable properties and adjusts their widths.
     */
    private void initializeColumns() {
        // Configure columns to use observable properties
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        addressColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() -> cellData.getValue().getAddress().toString(), cellData.getValue().getAddress().streetProperty(), cellData.getValue().getAddress().postalCodeProperty(), cellData.getValue().getAddress().cityProperty())
        );

        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        socialSecurityNumberColumn.setCellValueFactory(cellData -> cellData.getValue().socialSecurityNumberProperty());
        birthDateColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                                cellData.getValue().getBirthDate() != null ? cellData.getValue().getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "",
                        cellData.getValue().birthDateProperty())
        );

        // Set column widths as percentages of the TableView width
        firstNameColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.10));
        lastNameColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.10));
        addressColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.15));
        emailColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.15));
        phoneNumberColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.10));
        socialSecurityNumberColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.15));
        addressColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.25));
        actionColumn.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.10));
    }

    /**
     * Initializes the customer data for the TableView with some example customers.
     */
    private void initializeCustomerData() {
        // Utiliser les clients depuis CustomerDataStore
        customerTable.setItems(CustomerDataStore.getInstance().getCustomers());
    }

    /**
     * Sets up the search functionality for filtering customers in the TableView.
     */
    private void initializeSearchField() {
        FilteredList<Customer> filteredData = new FilteredList<>(CustomerDataStore.getInstance().getCustomers(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
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
     * Configures the action buttons (edit and delete) in the TableView.
     */
    private void initializeActionsColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button();
            private final Button deleteButton = new Button();

            {
                editButton.setGraphic(new FontIcon("fas-pen"));
                deleteButton.setGraphic(new FontIcon("fas-trash"));

                editButton.getStyleClass().add("button-warning");
                deleteButton.getStyleClass().add("button-destructive");

                editButton.setOnAction(event -> handleEditCustomer(getTableView().getItems().get(getIndex())));
                deleteButton.setOnAction(event -> handleDeleteCustomer(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(5);
                    hBox.getChildren().addAll(editButton, deleteButton);
                    setGraphic(hBox);
                }
            }
        });
    }

    @FXML
    private void handleAddCustomer() {
        try {
            // Charger le fichier FXML du formulaire client
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/CustomerForm.fxml"));
            DialogPane dialogPane = loader.load();

            // Obtenir le contrôleur du formulaire client
            CustomerFormController controller = loader.getController();
            // Créer un nouveau client vide et le passer au contrôleur
            controller.setCustomer(new Customer("", "", new Address("", "", ""), "", "", "", LocalDate.now(), "", ""));

            // Créer une boîte de dialogue pour le formulaire
            Dialog<Customer> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Ajouter un nouveau client");
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            // Vérifier les entrées lors de la validation
            Button saveButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                if (!controller.validateInputs()) { // Correction ici, on vérifie si la validation échoue
                    event.consume(); // Empêche la fermeture de la boîte de dialogue si les entrées ne sont pas valides
                }
            });

            // Définir le résultat de la boîte de dialogue si le bouton OK est cliqué
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    return controller.getCustomer(); // Obtenir le client du contrôleur si les données sont valides
                }
                return null;
            });

            // Afficher la boîte de dialogue et attendre l'action de l'utilisateur
            Optional<Customer> result = dialog.showAndWait();
            result.ifPresent(newCustomer -> {
                // Ajouter le nouveau client à CustomerDataStore
                CustomerDataStore.getInstance().addCustomer(newCustomer);
                updateStatusLabel("Le client a été créé avec succès.");
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleEditCustomer(Customer customer) {
        try {
            // Charger le fichier FXML du formulaire client
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/CustomerForm.fxml"));
            DialogPane dialogPane = loader.load();

            // Obtenir le contrôleur du formulaire client
            CustomerFormController controller = loader.getController();
            // Passer le client sélectionné au contrôleur pour le pré-remplissage du formulaire
            controller.setCustomer(customer);

            // Créer une boîte de dialogue pour le formulaire
            Dialog<Customer> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modification client");
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            // Vérifier les entrées lors de la validation
            Button saveButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                if (!controller.validateInputs()) { // Correction ici également
                    event.consume(); // Empêche la fermeture de la boîte de dialogue si les entrées ne sont pas valides
                }
            });

            dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? controller.getCustomer() : null);

            Optional<Customer> result = dialog.showAndWait();
            result.ifPresent(updatedCustomer -> {
                int index = CustomerDataStore.getInstance().getCustomers().indexOf(customer);
                if (index >= 0) {
                    CustomerDataStore.getInstance().getCustomers().set(index, updatedCustomer);
                    updateStatusLabel("Personne modifiée avec succès.");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Opens a confirmation dialog to delete the selected customer.
     * If confirmed, the customer is removed from the list.
     *
     * @param customer The customer to be deleted.
     */
    private void handleDeleteCustomer(Customer customer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette personne ?");
        alert.setContentText("Cette action est irréversible.");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/global.css")).toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        FontIcon icon = new FontIcon(FontAwesomeSolid.INFO_CIRCLE);
        icon.setIconSize(48);
        icon.setIconColor(Color.web("#E53935"));
        alert.setGraphic(icon);

        ButtonType yesButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Button yesBtn = (Button) dialogPane.lookupButton(yesButton);
        yesBtn.setStyle("-fx-background-color: #F44336;");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            CustomerDataStore.getInstance().removeCustomer(customer);
            updateStatusLabel("Personne supprimée avec succès.");
        }
    }

    /**
     * Updates the status label with a message and a CSS class.
     * The message is cleared after 3 seconds.
     *
     * @param message The message to display.
     */
    private void updateStatusLabel(String message) {
        statusLabel.setText(message);
        statusLabel.getStyleClass().setAll("alert", "alert-success");
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(3),
                ae -> {
                    statusLabel.setText("");
                    statusLabel.getStyleClass().clear();
                }
        ));
        timeline.play();
    }
}
