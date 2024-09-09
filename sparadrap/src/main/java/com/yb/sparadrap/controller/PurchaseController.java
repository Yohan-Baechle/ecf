package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.Purchase;
import com.yb.sparadrap.model.store.PurchaseDataStore;
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
 * Controller for managing purchases in the application.
 * Handles interactions with the purchase list and manages purchase addition, editing, and deletion.
 */
public class PurchaseController {

    @FXML
    private Button addPurchaseBtn;
    @FXML
    private TableView<Purchase> purchaseTable;
    @FXML
    private TableColumn<Purchase, String> medicationColumn;
    @FXML
    private TableColumn<Purchase, String> medicationCategoryColumn;
    @FXML
    private TableColumn<Purchase, String> quantityColumn;
    @FXML
    private TableColumn<Purchase, String> purchaseDateColumn;
    @FXML
    private TableColumn<Purchase, String> unitPriceColumn;
    @FXML
    private TableColumn<Purchase, String> totalPriceColumn;
    @FXML
    private TableColumn<Purchase, Void> actionColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label statusLabel;

    /**
     * Initializes the controller by setting up columns, purchase data, search functionality, and action buttons.
     */
    @FXML
    public void initialize() {
        initializeColumns();
        initializePurchaseData();
        initializeSearchField();
        initializeActionsColumn();
    }

    /**
     * Configures the columns of the TableView.
     * Sets up columns to use observable properties and adjusts their widths.
     */
    private void initializeColumns() {
        // Configure les colonnes pour utiliser les propriétés observables
        medicationColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                                cellData.getValue().getMedication() != null ? cellData.getValue().getMedication().getName() : "",
                        cellData.getValue().medicationProperty()
                )
        );
        medicationCategoryColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() -> cellData.getValue().getMedication().getCategory().getDisplayName())
        );
        quantityColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                                String.valueOf(cellData.getValue().getQuantity()),
                        cellData.getValue().quantityProperty()
                )
        );
        unitPriceColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(
                        () -> String.format("%.2f €", cellData.getValue().getMedication().getPrice()),
                        cellData.getValue().getMedication().priceProperty()
                )
        );
        totalPriceColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                                String.format("%.2f €", cellData.getValue().getTotalAmount()),
                        cellData.getValue().totalAmountProperty()
                )
        );
        purchaseDateColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                                cellData.getValue().getPurchaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        cellData.getValue().purchaseDateProperty()
                )
        );

        // Définir les largeurs des colonnes en pourcentage de la largeur du TableView
        medicationColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.30));
        medicationCategoryColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.15));
        quantityColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.10));
        unitPriceColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.10));
        totalPriceColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.10));
        purchaseDateColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.15));
        actionColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.10));
    }



    /**
     * Initializes the purchase data for the TableView with some example purchases.
     */
    private void initializePurchaseData() {
        // Utiliser les achats depuis PurchaseDataStore
        purchaseTable.setItems(PurchaseDataStore.getInstance().getPurchases());
    }

    /**
     * Sets up the search functionality for filtering purchases in the TableView.
     */
    private void initializeSearchField() {
        FilteredList<Purchase> filteredData = new FilteredList<>(PurchaseDataStore.getInstance().getPurchases(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(purchase -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                // Comparaison du médicament, catégorie et date
                boolean matchesMedication = purchase.getMedication() != null && purchase.getMedication().getName().toLowerCase().contains(lowerCaseFilter);
                boolean matchesCategory = purchase.getMedication().getCategory() != null && purchase.getMedication().getCategory().getDisplayName().toLowerCase().contains(lowerCaseFilter);

                // Formatage de la date de prescription ou de la date d'achat pour la comparaison
                boolean matchesDate = (purchase.getPrescriptionDate() != null && purchase.getPrescriptionDate().format(formatter).contains(lowerCaseFilter)) ||
                        (purchase.getPurchaseDate() != null && purchase.getPurchaseDate().format(formatter).contains(lowerCaseFilter));

                // Renvoie 'true' si l'une des conditions est remplie
                return matchesMedication || matchesCategory || matchesDate;
            });
        });

        SortedList<Purchase> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(purchaseTable.comparatorProperty());
        purchaseTable.setItems(sortedData);
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

                editButton.setOnAction(event -> handleEditPurchase(getTableView().getItems().get(getIndex())));
                deleteButton.setOnAction(event -> handleDeletePurchase(getTableView().getItems().get(getIndex())));
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
    private void handleAddPurchase() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/PurchaseForm.fxml"));
            DialogPane dialogPane = loader.load();

            PurchaseFormController controller = loader.getController();
            controller.setPurchase(new Purchase(new Customer(), LocalDate.now(), new Medication(), 1, null, null));

            Dialog<Purchase> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Ajouter un achat");
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            Button saveButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                System.out.println("Validation du formulaire...");
                if (controller.validateInputs()) {
                    event.consume();
                    System.out.println("Validation échouée.");
                } else {
                    System.out.println("Validation réussie.");
                }
            });

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    System.out.println("Formulaire validé, création de l'achat...");
                    return controller.getPurchase();
                }
                return null;
            });

            Optional<Purchase> result = dialog.showAndWait();
            result.ifPresent(newPurchase -> {
                System.out.println("Ajout de l'achat au data store...");
                PurchaseDataStore.getInstance().addPurchase(newPurchase);
                updateStatusLabel("Achat effectué avec succès.");
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleEditPurchase(Purchase purchase) {
        try {
            System.out.println("Début de la modification de l'achat...");

            // Charger le fichier FXML du formulaire client
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout/PurchaseForm.fxml"));
            DialogPane dialogPane = loader.load();

            // Obtenir le contrôleur du formulaire client
            PurchaseFormController controller = loader.getController();

            // Passer l'achat sélectionné au contrôleur pour le pré-remplissage du formulaire
            controller.setPurchase(purchase);

            // Créer une boîte de dialogue pour le formulaire
            Dialog<Purchase> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modification achat");
            dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            // Vérifier les entrées lors de la validation
            Button saveButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
                System.out.println("Validation des champs avant sauvegarde...");
                if (controller.validateInputs()) { // Correction ici également
                    System.out.println("Validation échouée. Empêche la fermeture de la boîte de dialogue.");
                    event.consume(); // Empêche la fermeture de la boîte de dialogue si les entrées ne sont pas valides
                }
            });

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    return controller.getPurchase();
                }
                return null;
            });

            // Afficher la boîte de dialogue et attendre la validation ou l'annulation
            Optional<Purchase> result = dialog.showAndWait();
            result.ifPresent(updatedPurchase -> {
                int index = PurchaseDataStore.getInstance().getPurchases().indexOf(purchase);
                if (index >= 0) {
                    PurchaseDataStore.getInstance().getPurchases().set(index, updatedPurchase);
                    updateStatusLabel("Achat modifié avec succès.");
                    System.out.println("Achat mis à jour avec succès.");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * Opens a confirmation dialog to delete the selected purchase.
     * If confirmed, the purchase is removed from the list.
     *
     * @param purchase The purchase to be deleted.
     */
    private void handleDeletePurchase(Purchase purchase) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet achat ?");
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
            PurchaseDataStore.getInstance().removePurchase(purchase);
            updateStatusLabel("Achat supprimé avec succès.");
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