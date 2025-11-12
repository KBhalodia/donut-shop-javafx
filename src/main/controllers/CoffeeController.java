package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Main;
import main.model.Coffee;
import main.enums.AddIns;
import main.enums.CupSize;

/**
 * Controller for Coffee Ordering View.
 * Handles user selections for size, add-ins, quantity, and subtotal calculation.
 */
public class CoffeeController {

    @FXML private ComboBox<CupSize> cupSizeCombo;
    @FXML private CheckBox whippedCreamCheck, vanillaCheck, milkCheck, caramelCheck, mochaCheck;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private Label subtotalLabel;

    private static final double ADD_IN_COST = 0.25;
    private static final double BASE_PRICE = 2.39;

    @FXML
    private void initialize() {
        // Populate size options
        cupSizeCombo.getItems().setAll(CupSize.values());
        cupSizeCombo.getSelectionModel().selectFirst();

        // Configure quantity spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        quantitySpinner.setValueFactory(valueFactory);

        updateSubtotal();

        // Listeners for real-time subtotal update
        cupSizeCombo.setOnAction(e -> updateSubtotal());
        quantitySpinner.valueProperty().addListener((obs, oldVal, newVal) -> updateSubtotal());
    }

    /** Handles Add to Order button click */
    @FXML
    private void onAddCoffee() {
        CupSize size = cupSizeCombo.getValue();
        int quantity = quantitySpinner.getValue();

        Coffee coffee = new Coffee(size, quantity);

        if (whippedCreamCheck.isSelected()) coffee.addAddIn(AddIns.WHIPPED_CREAM);
        if (vanillaCheck.isSelected()) coffee.addAddIn(AddIns.VANILLA);
        if (milkCheck.isSelected()) coffee.addAddIn(AddIns.MILK);
        if (caramelCheck.isSelected()) coffee.addAddIn(AddIns.CARAMEL);
        if (mochaCheck.isSelected()) coffee.addAddIn(AddIns.MOCHA);

        Main.currentOrder.addItem(coffee);
        showAlert("Coffee Added", "Coffee successfully added to current order!");
    }

    /** Handles Clear button click */
    @FXML
    private void onClear() {
        cupSizeCombo.getSelectionModel().selectFirst();
        whippedCreamCheck.setSelected(false);
        vanillaCheck.setSelected(false);
        milkCheck.setSelected(false);
        caramelCheck.setSelected(false);
        mochaCheck.setSelected(false);
        quantitySpinner.getValueFactory().setValue(1);
        updateSubtotal();
    }

    /** Updates subtotal label dynamically */
    private void updateSubtotal() {
        double subtotal = BASE_PRICE;
        CupSize size = cupSizeCombo.getValue();
        int quantity = quantitySpinner.getValue();

        // Size adjustments
        if (size == CupSize.TALL) subtotal += 0.60;
        else if (size == CupSize.GRANDE) subtotal += 1.20;
        else if (size == CupSize.VENTI) subtotal += 1.80;

        // Add-ins
        int addInCount = 0;
        if (whippedCreamCheck.isSelected()) addInCount++;
        if (vanillaCheck.isSelected()) addInCount++;
        if (milkCheck.isSelected()) addInCount++;
        if (caramelCheck.isSelected()) addInCount++;
        if (mochaCheck.isSelected()) addInCount++;

        subtotal += addInCount * ADD_IN_COST;
        subtotal *= quantity;

        subtotalLabel.setText(String.format("Subtotal: $%.2f", subtotal));
    }

    /** Helper for alert pop-ups */
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
