package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.nio.file.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import main.model.StoreOrders;
import main.Main; // to access the global storeOrders

public class AllOrdersController {

    @FXML private ListView<String> ordersList;
    @FXML private TextArea orderDetailArea;
    @FXML private Button cancelBtn, exportBtn;

    private static final double NJ_TAX = 0.06625;

    @FXML
    private void initialize() {
        reloadOrders();
    }

    @FXML
    private void onOrderSelected() {
        // load details of the selected order
        int index = ordersList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            orderDetailArea.setText(Main.storeOrders.getOrders().get(index).toString());
        }
    }

    @FXML
    private void onCancelOrder() {
        int index = ordersList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Main.storeOrders.removeOrder(Main.storeOrders.getOrders().get(index));
            reloadOrders();
            orderDetailArea.clear();
        }
    }

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

    private void reloadOrders() {
        ordersList.getItems().clear();
        for (int i = 0; i < Main.storeOrders.getOrders().size(); i++) {
            var order = Main.storeOrders.getOrders().get(i);
            ordersList.getItems().add("Order #" + order.getOrderNumber());
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
