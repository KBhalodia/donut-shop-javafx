package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Main;
import main.model.MenuItem;
import main.model.Order;

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
        if (Main.currentOrder.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Order");
            alert.setHeaderText(null);
            alert.setContentText("There are no items in the current order.");
            alert.showAndWait();
            return;
        }

        // Add current order to the store's list
        Main.storeOrders.addOrder(Main.currentOrder);

        int placedOrderNumber = Main.currentOrder.getOrderNumber();

        // Start a brand new current order
        Main.currentOrder = new main.model.Order();

        // Refresh this screen
        refreshTotals();

        // Tell the user it worked
        Alert confirm = new Alert(Alert.AlertType.INFORMATION);
        confirm.setTitle("Order Placed");
        confirm.setHeaderText("Order #" + placedOrderNumber + " placed.");
        confirm.setContentText("The order has been added to Store Orders.");
        confirm.showAndWait();
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
    @FXML
    private void onBackToMainMenu(javafx.event.ActionEvent event) {
        try {
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(
                    getClass().getResource("/main-view.fxml")
            );
            javafx.stage.Stage stage =
                    (javafx.stage.Stage) ((javafx.scene.Node) event.getSource())
                            .getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
