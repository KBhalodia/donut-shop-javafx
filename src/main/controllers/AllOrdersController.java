package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.nio.file.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import main.Main;
/**
 * Controller for the store orders view.
 * Displays all placed orders, allows cancellation of a selected order,
 * shows order details, and supports exporting all orders to a text file.
 */
public class AllOrdersController {

    @FXML private ListView<String> ordersList;
    @FXML private TextArea orderDetailArea;
    @FXML private Button cancelBtn, exportBtn;

    private static final double NJ_TAX = 0.06625;
    /**
     * Initializes the store orders view by setting the window title,
     * registering this controller with {@link Main}, and loading
     * the current list of store orders.
     */
    @FXML
    private void initialize() {
        // Set window title
        if (ordersList != null && ordersList.getScene() != null) {
            javafx.stage.Stage stage = (javafx.stage.Stage) ordersList.getScene().getWindow();
            if (stage != null) {
                stage.setTitle("Store Orders");
            }
        }
        
        // register this instance in Main so other controllers can ask it to refresh
        Main.allOrdersController = this;
        reloadOrders();
    }
    /**
     * Handles selection changes in the orders list.
     * When an order is selected, its details (subtotal, tax, total, and items)
     * are displayed in the text area.
     */
    @FXML
    private void onOrderSelected() {
        int index = ordersList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            var order = Main.storeOrders.getOrders().get(index);

            StringBuilder sb = new StringBuilder();
            sb.append("Order #").append(order.getOrderNumber()).append("\n");
            sb.append("Subtotal: $").append(String.format("%.2f", order.getSubtotal())).append("\n");
            sb.append("Tax: $").append(String.format("%.2f", order.getTax())).append("\n");
            sb.append("Total: $").append(String.format("%.2f", order.getTotal())).append("\n\n");
            sb.append("Items:\n");
            for (var item : order.getItems()) {
                sb.append(" • ").append(item.toString()).append("\n");
            }

            orderDetailArea.setText(sb.toString());
        }
    }
    /**
     * Cancels (removes) the selected order from the store orders list,
     * then reloads the list and clears the detail area.
     */
    @FXML
    private void onCancelOrder() {
        int index = ordersList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Main.storeOrders.removeOrder(Main.storeOrders.getOrders().get(index));
            reloadOrders();
            orderDetailArea.clear();
        }
    }
    /**
     * Exports all store orders to a text file named "StoreOrders.txt"
     * in the working directory. Shows an alert indicating success or failure.
     */
    @FXML
    private void onExport() {
        try {
            Path path = Paths.get("StoreOrders.txt");
            String content = Main.storeOrders.export();
            Files.writeString(path, content, StandardCharsets.UTF_8);
            showAlert("Export Successful", "Orders exported to " + path.toAbsolutePath());
        } catch (IOException ex) {
            showAlert("Export Failed", "Error writing to file: " + ex.getMessage());
        }
    }
    /**
     * Reloads the list of store orders from {@link Main#storeOrders}
     * and updates the list view with order number and total for each order.
     * This method is public so other controllers can trigger refreshes.
     */

    public void reloadOrders() {
        ordersList.getItems().clear();
        for (var order : Main.storeOrders.getOrders()) {
            ordersList.getItems().add(
                    "Order #" + order.getOrderNumber() +
                            " — Total $" + String.format("%.2f", order.getTotal())
            );
        }
    }
    /**
     * Displays an informational alert with the given title and message.
     *
     * @param title title for the alert dialog
     * @param msg   message body for the alert
     */
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
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
