package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SandwichController {

    @FXML private ToggleGroup proteinGroup; // beef, chicken, salmon (RadioButtons in FXML)
    @FXML private ToggleGroup breadGroup;   // bagel, wheat, sourdough
    @FXML private CheckBox cheese, lettuce, tomato, onion;
    @FXML private Spinner<Integer> qtySpinner;
    @FXML private Label priceLabel;
    @FXML private Button addBtn;

    @FXML
    private void initialize() {
        // TODO: set qty spinner; add listeners to toggles/checkboxes -> updatePrice()
        updatePrice();
    }

    @FXML
    private void onSelectionChanged() {
        updatePrice();
    }

    @FXML
    private void onAddToOrder() {
        // TODO: build Sandwich model with selected protein/bread/add-ons, * qty; add to CURRENT order
    }

    private void updatePrice() {
        // TODO: base by protein; +$1 cheese; +$0.30 per veggie; * qty
        priceLabel.setText("$0.00");
    }
}
