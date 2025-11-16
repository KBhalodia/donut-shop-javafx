package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Main;
import main.model.MenuItem;
import main.model.Order;
/**
 * Controller for the current order view.
 * Displays all items in the current order, allows items to be removed or cleared,
 * and lets the user place the order into the store-wide list of orders.
 */
public class CurrentOrderController {

    @FXML private ListView<String> itemList; // display each item's summary string
    @FXML private Label subtotalLabel, taxLabel, totalLabel;
    @FXML private Button removeSelectedBtn, clearBtn, placeOrderBtn;
    /**
     * Initializes the current order view by setting the window title,
     * registering this controller with {@link Main}, and refreshing
     * the displayed items and totals.
     */
    @FXML
    private void initialize() {
        // Set window title
        if (itemList != null && itemList.getScene() != null) {
            javafx.stage.Stage stage = (javafx.stage.Stage) itemList.getScene().getWindow();
            if (stage != null) {
                stage.setTitle("Current Order");
            }
        }
        
        // Register this controller with Main so it can be refreshed from other controllers
        main.Main.currentOrderController = this;
        refreshTotals();
    }
    /**
     * Handles the removal of the selected item from the current order.
     * Shows a warning alert if no item is selected.
     */

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
    /**
     * Handles clearing all items from the current order.
     * Prompts for confirmation before deleting non-empty orders.
     */
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
    /**
     * Handles placing the current order into the store orders list.
     * If the order is empty, shows a warning. Otherwise it adds the
     * current order to {@link Main#storeOrders}, creates a new current
     * order, and shows a confirmation alert.
     */
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
     * This method reads from {@link Main#currentOrder} and updates the UI.
     * Can be called from other controllers when the current order changes.
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
    /**
     * Handles navigation back to the main menu view (main-view.fxml).
     * Preserves the current window size and reapplies the stylesheet.
     *
     * @param event the action event triggered by the Back button
     */
    @FXML
    private void onBackToMainMenu(javafx.event.ActionEvent event) {
        try {
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(
                    getClass().getResource("/main-view.fxml")
            );
            javafx.stage.Stage stage =
                    (javafx.stage.Stage) ((javafx.scene.Node) event.getSource())
                            .getScene().getWindow();
            
            // Preserve window size
            double width = stage.getWidth();
            double height = stage.getHeight();
            
            javafx.scene.Scene scene = stage.getScene();
            if (scene == null) {
                scene = new javafx.scene.Scene(root, width, height);
                scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
                stage.setScene(scene);
            } else {
                scene.setRoot(root);
                // Ensure minimum size is maintained
                if (width < 800) width = 900;
                if (height < 600) height = 700;
                stage.setWidth(width);
                stage.setHeight(height);
            }
            stage.setTitle("RU Cafe");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
