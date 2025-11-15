package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;

public class MainController {

    @FXML
    private TabPane mainTabPane;

    @FXML
    private GridPane homeScreen;

    @FXML
    private VBox donutsBox;

    @FXML
    private VBox coffeeBox;

    @FXML
    private VBox sandwichBox;

    @FXML
    private Button currentOrderBtn;

    @FXML
    private Button ordersPlacedBtn;

    @FXML
    private void initialize() {
        // TabPane starts hidden (it's already visible="false" in FXML)
        
        // Add mouse click handlers to menu item boxes
        if (donutsBox != null) {
            donutsBox.setOnMouseClicked(e -> showDonutsTab());
        }
        if (coffeeBox != null) {
            coffeeBox.setOnMouseClicked(e -> showCoffeeTab());
        }
        if (sandwichBox != null) {
            sandwichBox.setOnMouseClicked(e -> showSandwichTab());
        }

        // Set up button content for Current Order button
        if (currentOrderBtn != null) {
            HBox currentOrderContent = new HBox(15);
            currentOrderContent.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            
            ImageView cartIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/cart.png")));
            cartIcon.setFitWidth(30);
            cartIcon.setFitHeight(30);
            cartIcon.setPreserveRatio(true);
            
            Label currentOrderLabel = new Label("Current Order");
            currentOrderLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
            
            currentOrderContent.getChildren().addAll(cartIcon, currentOrderLabel);
            currentOrderBtn.setGraphic(currentOrderContent);
        }

        // Set up button content for Orders Placed button
        if (ordersPlacedBtn != null) {
            HBox ordersPlacedContent = new HBox(15);
            ordersPlacedContent.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            
            Label receiptIcon = new Label("🧾");
            receiptIcon.setStyle("-fx-font-size: 24px;");
            
            Label ordersPlacedLabel = new Label("Orders Placed");
            ordersPlacedLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
            
            ordersPlacedContent.getChildren().addAll(receiptIcon, ordersPlacedLabel);
            ordersPlacedBtn.setGraphic(ordersPlacedContent);
        }
    }

    private void showTabsAndSelect(int index) {
        homeScreen.setVisible(false);
        homeScreen.setManaged(false);

        mainTabPane.setVisible(true);
        mainTabPane.setManaged(true);

        mainTabPane.getSelectionModel().select(index);

        // Refresh if going to current order
        if (index == 3 && Main.currentOrderController != null) {
            Main.currentOrderController.refreshTotals();
        }
    }

    @FXML private void showCoffeeTab()       { showTabsAndSelect(0); }
    @FXML private void showDonutsTab()       { showTabsAndSelect(1); }
    @FXML private void showSandwichTab()     { showTabsAndSelect(2); }
    @FXML private void showCurrentOrderTab() { showTabsAndSelect(3); }
    @FXML private void showStoreOrdersTab()  { showTabsAndSelect(4); }
}
