package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.nio.file.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import main.Main;

public class AllOrdersController {

    @FXML private ListView<String> ordersList;
    @FXML private TextArea orderDetailArea;
    @FXML private Button cancelBtn, exportBtn;

    private static final double NJ_TAX = 0.06625;

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

    // ⬅️ make this PUBLIC so other controllers can call it
    public void reloadOrders() {
        ordersList.getItems().clear();
        for (var order : Main.storeOrders.getOrders()) {
            ordersList.getItems().add(
                    "Order #" + order.getOrderNumber() +
                            " — Total $" + String.format("%.2f", order.getTotal())
            );
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
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
