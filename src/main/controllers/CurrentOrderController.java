package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CurrentOrderController {

    @FXML private ListView<String> itemList; // display each item's summary string
    @FXML private Label subtotalLabel, taxLabel, totalLabel;
    @FXML private Button removeSelectedBtn, clearBtn, placeOrderBtn;

    private static final double NJ_TAX = 0.06625; // 6.625%

    @FXML
    private void initialize() {
        refreshTotals();
    }

    @FXML
    private void onRemoveSelected() {
        // TODO: remove selected item from CURRENT order
        refreshTotals();
    }

    @FXML
    private void onClearAll() {
        // TODO: clear CURRENT order items
        refreshTotals();
    }

    @FXML
    private void onPlaceOrder() {
        // TODO: assign order number (from StoreOrders), push CURRENT into StoreOrders, start a new CURRENT
        // show a confirmation dialog with order #
        refreshTotals();
    }

    private void refreshTotals() {
        // TODO: read CURRENT order items & prices
        double sub = 0.0;
        double tax = sub * NJ_TAX;
        double total = sub + tax;

        subtotalLabel.setText(String.format("$%.2f", sub));
        taxLabel.setText(String.format("$%.2f", tax));
        totalLabel.setText(String.format("$%.2f", total));
    }
}
