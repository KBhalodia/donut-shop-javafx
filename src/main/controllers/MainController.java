package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller for the Main Menu (main-view.fxml).
 * Handles navigation to other screens in the SAME window.
 */
public class MainController {

    /** Helper to switch to a different FXML in the same Stage */
    private void switchView(String fxmlFile, String title, ActionEvent event) {
        try {
            // All your FXML files are at the root of resources: /coffee-view.fxml, etc.
            Parent root = FXMLLoader.load(getClass().getResource("/" + fxmlFile));

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();
            
            // Preserve window size
            double width = stage.getWidth();
            double height = stage.getHeight();
            
            Scene newScene = new Scene(root, width, height);
            newScene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
            stage.setScene(newScene);
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // good enough for debugging
        }
    }

    @FXML
    private void onOpenCoffee(ActionEvent event) {
        switchView("coffee-view.fxml", "Ordering Coffee", event);
    }

    @FXML
    private void onOpenDonuts(ActionEvent event) {
        switchView("donut-view.fxml", "Ordering Donuts", event);
    }

    @FXML
    private void onOpenSandwich(ActionEvent event) {
        switchView("sandwich-view.fxml", "Ordering Sandwich", event);
    }

    @FXML
    private void onOpenCurrentOrder(ActionEvent event) {
        switchView("current-order-view.fxml", "Current Order", event);
    }

    @FXML
    private void onOpenStoreOrders(ActionEvent event) {
        //THIS MUST MATCH YOUR FXML FILE NAME
        switchView("all-orders-view.fxml", "Store Orders", event);
    }
}
