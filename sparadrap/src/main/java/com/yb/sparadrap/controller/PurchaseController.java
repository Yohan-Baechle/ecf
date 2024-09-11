package com.yb.sparadrap.controller;

import com.yb.sparadrap.model.Customer;
import com.yb.sparadrap.model.Medication;
import com.yb.sparadrap.model.Purchase;
import com.yb.sparadrap.store.PurchaseDataStore;
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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contrôleur pour la gestion des achats dans l'application.
 * Gère les interactions avec la liste des achats et les actions d'ajout, d'édition et de suppression.
 */
public class PurchaseController {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML
    private Button addPurchaseBtn;
    @FXML
    private TableView<Purchase> purchaseTable;
    @FXML
    private TableColumn<Purchase, String> medicationBasketColumn;
    @FXML
    private TableColumn<Purchase, String> purchaseTypeColumn;
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
     * Initialise le contrôleur : configuration des colonnes, des données achats,
     * de la barre de recherche et des boutons d'action.
     */
    @FXML
    public void initialize() {
        initializeColumns();
        initializePurchaseData();
        initializeSearchField();
        initializeActionsColumn();

        purchaseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_NEXT_COLUMN);
    }

    /**
     * Configure les colonnes du TableView avec les propriétés observables des achats.
     */
    private void initializeColumns() {
        // Colonne du panier de médicaments
        medicationBasketColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                        cellData.getValue().getMedicationBasket().entrySet().stream()
                                .map(entry -> entry.getKey().getName() + " (x" + entry.getValue() + ")")
                                .collect(Collectors.joining(", "))
                )
        );

        // Colonne pour le type d'achat (direct ou avec ordonnance)
        purchaseTypeColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                        cellData.getValue().getPrescribingDoctor() == null ? "Direct" : "Avec ordonnance"
                )
        );

        // Quantité : la somme des quantités dans le panier
        quantityColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                        String.valueOf(cellData.getValue().getMedicationBasket().values().stream()
                                .mapToInt(Integer::intValue)
                                .sum())
                )
        );

        unitPriceColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                        cellData.getValue().getMedicationBasket().entrySet().stream()
                                .map(entry -> String.format("%s: %.2f €", entry.getKey().getName(), entry.getKey().getPrice()))
                                .collect(Collectors.joining(", "))
                )
        );

        totalPriceColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                        String.format("%.2f €", cellData.getValue().getTotalAmount())
                )
        );

        purchaseDateColumn.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                        cellData.getValue().getPurchaseDate().format(dateFormatter)
                )
        );

        // Configuration des largeurs des colonnes
        medicationBasketColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.25));
        purchaseTypeColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.10));
        quantityColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.10));
        unitPriceColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.15));
        totalPriceColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.10));
        purchaseDateColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.15));
        actionColumn.prefWidthProperty().bind(purchaseTable.widthProperty().multiply(0.10));
    }

    /**
     * Initialise les données des achats à partir de PurchaseDataStore.
     */
    private void initializePurchaseData() {
        purchaseTable.setItems(PurchaseDataStore.getInstance().getPurchases());
    }

    /**
     * Initialise la barre de recherche pour filtrer les achats dans le TableView.
     */
    private void initializeSearchField() {
        FilteredList<Purchase> filteredData = new FilteredList<>(PurchaseDataStore.getInstance().getPurchases(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(purchase -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Affiche tous les achats si le champ de recherche est vide
                }

                String lowerCaseFilter = newValue.toLowerCase();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                // Comparaison du panier et de la date
                boolean matchesMedication = purchase.getMedicationBasket().keySet().stream()
                        .anyMatch(medication -> medication.getName().toLowerCase().contains(lowerCaseFilter));
                boolean matchesDate = purchase.getPurchaseDate() != null && purchase.getPurchaseDate().format(formatter).contains(lowerCaseFilter);

                return matchesMedication || matchesDate;
            });
        });

        SortedList<Purchase> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(purchaseTable.comparatorProperty());
        purchaseTable.setItems(sortedData);
    }

    /**
     * Configure les boutons d'action (édition et suppression) pour chaque ligne du TableView.
     */
    private void initializeActionsColumn() {
        PurchaseController controller = this;

        actionColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null); // Si la cellule est vide, ne rien afficher
                } else {
                    Purchase purchase = getTableView().getItems().get(getIndex());
                    setGraphic(ActionButtonUtil.createEditDeleteButtons(purchase,
                            controller::handleEditPurchase,
                            controller::handleDeletePurchase));
                }
            }
        });
    }

    /**
     * Gère l'ajout d'un nouvel achat à l'aide d'une boîte de dialogue.
     */
    @FXML
    private void handleAddPurchase() {
        // Créer un panier vide pour les médicaments
        Map<Medication, Integer> medicationBasket = new HashMap<>();

        // Créer un nouvel achat avec un panier vide et les autres valeurs par défaut
        Purchase newPurchase = new Purchase(new Customer(), LocalDate.now(), medicationBasket, null, null);

        // Ouvrir le formulaire d'ajout/modification
        openPurchaseForm(newPurchase, "Ajouter un nouvel achat");
    }

    /**
     * Gère la modification d'un achat existant.
     *
     * @param purchase L'achat à modifier.
     */
    @FXML
    private void handleEditPurchase(Purchase purchase) {
        openPurchaseForm(purchase, "Modification achat");
    }

    /**
     * Ouvre un formulaire d'achat pour ajouter ou éditer un achat.
     *
     * @param purchase    L'achat à ajouter ou modifier.
     * @param dialogTitle Le titre de la boîte de dialogue.
     */
    private void openPurchaseForm(Purchase purchase, String dialogTitle) {
        EntityDialogUtil.openEntityFormDialog("/fxml/layout/PurchaseForm.fxml", dialogTitle,
                controller -> {
                    PurchaseFormController purchaseFormController = (PurchaseFormController) controller;
                    purchaseFormController.setPurchase(purchase);
                },
                controller -> ((PurchaseFormController) controller).getPurchase()
        ).ifPresent(updatedPurchase -> {
            if (purchaseTable.getItems().contains(purchase)) {
                int index = PurchaseDataStore.getInstance().getPurchases().indexOf(purchase);
                PurchaseDataStore.getInstance().getPurchases().set(index, updatedPurchase);
                AlertUtil.updateStatusLabel(statusLabel, "L'achat a été modifié avec succès.", "success");
            } else {
                PurchaseDataStore.getInstance().addPurchase(updatedPurchase);
                AlertUtil.updateStatusLabel(statusLabel, "L'achat a été créé avec succès.", "success");
            }
        });
    }

    /**
     * Gère la suppression d'un achat avec confirmation.
     *
     * @param purchase L'achat à supprimer.
     */
    private void handleDeletePurchase(Purchase purchase) {
        DeleteUtil.handleDelete(purchase,
                PurchaseDataStore.getInstance()::removePurchase,
                "L'achat a été supprimé avec succès.",
                statusLabel);
    }
}
