package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Main;
import main.model.MenuItem;

public class CurrentOrderController {

    @FXML private ListView<String> itemList; // display each item's summary string
    @FXML private Label subtotalLabel, taxLabel, totalLabel;
    @FXML private Button removeSelectedBtn, clearBtn, placeOrderBtn;

    @FXML
    private void initialize() {
        // Register this controller with Main so it can be refreshed from other controllers
        main.Main.currentOrderController = this;
        refreshTotals();
    }

    @FXML
    private void onRemoveSelected() {
        int selectedIndex = itemList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < Main.currentOrder.getItems().size()) {
            MenuItem itemToRemove = Main.currentOrder.getItems().get(selectedIndex);
            Main.currentOrder.removeItem(itemToRemove);
            refreshTotals();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item to remove.");
            alert.showAndWait();
        }
    }

    @FXML
    private void onClearAll() {
        if (Main.currentOrder.getItems().isEmpty()) {
            return;
        }
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Clear Order");
        confirmAlert.setHeaderText("Clear all items?");
        confirmAlert.setContentText("Are you sure you want to remove all items from the current order?");
        
        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Main.currentOrder.clear();
            refreshTotals();
        }
    }

    @FXML
    private void onPlaceOrder() {
        // TODO: assign order number (from StoreOrders), push CURRENT into StoreOrders, start a new CURRENT
        // show a confirmation dialog with order #
        refreshTotals();
    }

    /**
     * Refreshes the display with current order items and totals.
     * This method reads from Main.currentOrder and updates the UI.
     */
    public void refreshTotals() {
        // Update item list
        itemList.getItems().clear();
        for (MenuItem item : Main.currentOrder.getItems()) {
            itemList.getItems().add(item.toString());
        }

        // Calculate and display totals using Order's methods
        double sub = Main.currentOrder.getSubtotal();
        double tax = Main.currentOrder.getTax();
        double total = Main.currentOrder.getTotal();

        subtotalLabel.setText(String.format("$%.2f", sub));
        taxLabel.setText(String.format("$%.2f", tax));
        totalLabel.setText(String.format("$%.2f", total));
    }
}
