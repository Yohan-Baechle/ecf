package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Customer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Optional;

/**
 * Controller for managing users in the application.
 * Handles interactions with the user list and manages user addition, editing, and deletion.
 */
public class CustomerController {

    @FXML
    private Button addCustomerBtn;
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, String> lastNameColumn;
    @FXML
    private TableColumn<Customer, String> firstNameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    @FXML
    private TableColumn<Customer, String> cityColumn;
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

    private ObservableList<Customer> customerData;

    /**
     * Initializes the controller by setting up columns, user data, search functionality, and action buttons.
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
        firstNameColumn.prefWidthProperty().bind(customersTable.widthProperty().multiply(0.15));
        lastNameColumn.prefWidthProperty().bind(customersTable.widthProperty().multiply(0.15));
        addressColumn.prefWidthProperty().bind(customersTable.widthProperty().multiply(0.15));
        emailColumn.prefWidthProperty().bind(customersTable.widthProperty().multiply(0.15));
        phoneNumberColumn.prefWidthProperty().bind(customersTable.widthProperty().multiply(0.15));
        socialSecurityNumberColumn.prefWidthProperty().bind(customersTable.widthProperty().multiply(0.15));
        addressColumn.prefWidthProperty().bind(customersTable.widthProperty().multiply(0.30));
        actionColumn.prefWidthProperty().bind(customersTable.widthProperty().multiply(0.10));
    }

    /**
     * Initializes the user data for the TableView with some example customers.
     */
    private void initializeCustomerData() {
        customerData = FXCollections.observableArrayList(
                new Customer("Jean", "Dupont",
                        new Address("12 rue de la Paix", "75002", "Paris"),
                        "0612345678", "jean.dupont@example.com",
                        "192073409812345", LocalDate.of(1980, 5, 12), "Mutuelle A", "Dr. Bernard Martin"),

                new Customer("Marie", "Lefevre",
                        new Address("25 avenue des Champs-Élysées", "75008", "Paris"),
                        "0623456789", "marie.lefevre@example.com",
                        "283067509825674", LocalDate.of(1975, 3, 22), "Mutuelle B", "Dr. Anne Dupuis"),

                new Customer("Paul", "Moreau",
                        new Address("14 rue des Fleurs", "69002", "Lyon"),
                        "0645678910", "paul.moreau@example.com",
                        "170023456789012", LocalDate.of(1990, 11, 3), "Mutuelle C", "Dr. Sophie Girard"),

                new Customer("Lucie", "Durand",
                        new Address("58 boulevard Victor Hugo", "06000", "Nice"),
                        "0654321098", "lucie.durand@example.com",
                        "185089012345678", LocalDate.of(1985, 8, 15), "Mutuelle D", "Dr. Pierre Lefevre"),

                new Customer("Pierre", "Dubois",
                        new Address("34 rue de la République", "13001", "Marseille"),
                        "0678912345", "pierre.dubois@example.com",
                        "197054308765432", LocalDate.of(1970, 12, 7), "Mutuelle E", "Dr. Jacques Bernard")
        );

        customersTable.setItems(customerData);
    }

    /**
     * Sets up the search functionality for filtering users in the TableView.
     */
    private void initializeSearchField() {
        FilteredList<Customer> filteredData = new FilteredList<>(customerData, b -> true);

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
        sortedData.comparatorProperty().bind(customersTable.comparatorProperty());
        customersTable.setItems(sortedData);
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

//                editButton.setOnAction(event -> editCustomer(getTableView().getItems().get(getIndex())));
//                deleteButton.setOnAction(event -> deleteCustomer(getTableView().getItems().get(getIndex())));
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

    /**
     * Opens a confirmation dialog to delete the selected user.
     * If confirmed, the user is removed from the list.
     *
     * @param customer The user to be deleted.
     */
    private void deleteUser(Customer customer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette personne ?");
        alert.setContentText("Cette action est irréversible.");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/global.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        FontIcon icon = new FontIcon(FontAwesomeSolid.INFO);
        icon.setIconSize(48);
        icon.setIconColor(Color.web("#2E1274"));
        alert.setGraphic(icon);

        ButtonType yesButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Button yesBtn = (Button) dialogPane.lookupButton(yesButton);
        yesBtn.setStyle("-fx-background-color: #F44336;");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            customerData.remove(customer);
            updateStatusLabel("La personne a été supprimé avec succès.", "alert-success");
        }
    }

    /**
     * Updates the status label with a message and a CSS class.
     * The message is cleared after 3 seconds.
     *
     * @param message  The message to display.
     * @param cssClass The CSS class to apply for styling.
     */
    private void updateStatusLabel(String message, String cssClass) {
        statusLabel.setText(message);
        statusLabel.getStyleClass().setAll("alert", cssClass);
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