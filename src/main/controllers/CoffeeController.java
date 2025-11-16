package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Main;
import main.model.Coffee;
import main.enums.AddIns;
import main.enums.CupSize;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller for the coffee ordering view.
 * Manages cup size, add-ins, quantity selection, and live subtotal calculation,
 * and allows the user to add a coffee order to the current order.
 */
public class CoffeeController {

    @FXML private ComboBox<CupSize> cupSizeCombo;
    @FXML private CheckBox whippedCreamCheck, vanillaCheck, milkCheck, caramelCheck, mochaCheck;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private Label subtotalLabel;

    private static final double ADD_IN_COST = 0.25;
    private static final double BASE_PRICE = 2.39;
    /**
     * Initializes the coffee ordering view by setting up the cup size options,
     * quantity spinner, listeners for controls, and computing the initial subtotal.
     */
    @FXML
    private void initialize() {
        // Set window title
        if (cupSizeCombo != null && cupSizeCombo.getScene() != null) {
            Stage stage = (Stage) cupSizeCombo.getScene().getWindow();
            if (stage != null) {
                stage.setTitle("Ordering Coffee");
            }
        }
        
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
        
        // Listeners for add-in checkboxes
        whippedCreamCheck.setOnAction(e -> updateSubtotal());
        vanillaCheck.setOnAction(e -> updateSubtotal());
        milkCheck.setOnAction(e -> updateSubtotal());
        caramelCheck.setOnAction(e -> updateSubtotal());
        mochaCheck.setOnAction(e -> updateSubtotal());
    }

    /**
     * Handles the "Add to Order" button click.
     * Creates a {@link Coffee} object based on the user's selections, adds it
     * to {@link Main#currentOrder}, refreshes the current order view if open,
     * and shows a confirmation alert.
     */
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
        // Refresh the Current Order view if it's available
        if (Main.currentOrderController != null) {
            Main.currentOrderController.refreshTotals();
        }
        showAlert("Coffee Added", "Coffee successfully added to current order!");
    }

    /**
     * Handles the "Clear" button click.
     * Resets the cup size, unchecks all add-ins, resets quantity to 1,
     * and recomputes the subtotal.
     */
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

    /**
     * Recalculates and updates the subtotal label using the {@link Coffee} model's
     * price calculation. If no size is selected, the subtotal is set to $0.00.
     */
    private void updateSubtotal() {
        CupSize size = cupSizeCombo.getValue();
        
        if (size == null) {
            subtotalLabel.setText("Subtotal: $0.00");
            return;
        }
        
        int quantity = quantitySpinner.getValue();

        // Create a temporary Coffee object to use its price calculation
        Coffee tempCoffee = new Coffee(size, quantity);
        
        // Add selected add-ins
        if (whippedCreamCheck.isSelected()) tempCoffee.addAddIn(AddIns.WHIPPED_CREAM);
        if (vanillaCheck.isSelected()) tempCoffee.addAddIn(AddIns.VANILLA);
        if (milkCheck.isSelected()) tempCoffee.addAddIn(AddIns.MILK);
        if (caramelCheck.isSelected()) tempCoffee.addAddIn(AddIns.CARAMEL);
        if (mochaCheck.isSelected()) tempCoffee.addAddIn(AddIns.MOCHA);

        // Use the model's price calculation for consistency
        double subtotal = tempCoffee.price();
        subtotalLabel.setText(String.format("Subtotal: $%.2f", subtotal));
    }

    /**
     * Displays an informational alert dialog with the given title and message.
     *
     * @param title the alert window title
     * @param msg   the alert message body
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
     *      * @param event the action event triggered by the Back button
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
