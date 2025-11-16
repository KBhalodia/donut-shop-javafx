package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Main;
import main.enums.Bread;
import main.enums.Protein;
import main.enums.AddOns;
import main.model.Sandwich;
import main.model.MenuItem;
/**
 * Controller for the sandwich ordering view.
 * Manages bread, protein, add-on selections and quantity, provides live
 * price updates, and allows adding a sandwich to the current order.
 */
public class SandwichController {

    @FXML private ToggleGroup proteinGroup; // beef, chicken, salmon
    @FXML private ToggleGroup breadGroup;   // bagel, wheat, sourdough
    @FXML private CheckBox cheese, lettuce, tomato, onion;
    @FXML private Spinner<Integer> qtySpinner;
    @FXML private Label priceLabel;
    @FXML private Button addBtn;
    /**
     * Initializes the sandwich ordering view by configuring the quantity spinner,
     * registering listeners on all controls, and computing the initial price.
     */
    @FXML
    private void initialize() {
        // Set window title
        if (qtySpinner != null && qtySpinner.getScene() != null) {
            javafx.stage.Stage stage = (javafx.stage.Stage) qtySpinner.getScene().getWindow();
            if (stage != null) {
                stage.setTitle("Ordering Sandwich");
            }
        }
        
        // quantity 1–10
        SpinnerValueFactory<Integer> vf =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        qtySpinner.setValueFactory(vf);

        // Any selection change → re-compute price
        proteinGroup.selectedToggleProperty().addListener((obs, o, n) -> updatePrice());
        breadGroup.selectedToggleProperty().addListener((obs, o, n) -> updatePrice());

        cheese.setOnAction(e -> updatePrice());
        lettuce.setOnAction(e -> updatePrice());
        tomato.setOnAction(e -> updatePrice());
        onion.setOnAction(e -> updatePrice());

        qtySpinner.valueProperty().addListener((obs, o, n) -> updatePrice());

        updatePrice();
    }
    /**
     * Handles selection changes for any of the sandwich controls.
     * Simply recomputes the price using the current selections.
     */
    @FXML
    private void onSelectionChanged() {
        updatePrice();
    }
    /**
     * Handles the "Add to Order" button click.
     * Creates a {@link Sandwich} based on user selections, adds it to
     * {@link Main#currentOrder}, refreshes the current order view if open,
     * and shows a confirmation alert. If a required selection is missing,
     * a warning alert is shown instead.
     */
    @FXML
    private void onAddToOrder() {
        Protein protein = getSelectedProtein();
        Bread bread = getSelectedBread();
        Integer qty = qtySpinner.getValue();

        if (protein == null || bread == null || qty == null) {
            showAlert("Missing Selection", "Please select a bread, protein, and quantity.");
            return;
        }

        Sandwich s = new Sandwich(bread, protein, qty);

        if (cheese.isSelected()) s.addAddOn(AddOns.CHEESE);
        if (lettuce.isSelected()) s.addAddOn(AddOns.LETTUCE);
        if (tomato.isSelected()) s.addAddOn(AddOns.TOMATOES);
        if (onion.isSelected()) s.addAddOn(AddOns.ONIONS);

        Main.currentOrder.addItem(s);

        if (Main.currentOrderController != null) {
            Main.currentOrderController.refreshTotals();
        }

        showAlert("Sandwich Added", "Sandwich added to current order.");
    }
    /**
     * Recomputes the price label based on the current sandwich selections,
     * including bread, protein, add-ons, and quantity. When required fields
     * are missing, the price is shown as $0.00.
     */
    private void updatePrice() {
        Protein protein = getSelectedProtein();
        Bread bread = getSelectedBread(); // bread doesn’t change price but used to validate
        Integer qty = qtySpinner.getValue();

        if (protein == null || bread == null || qty == null) {
            priceLabel.setText("$0.00");
            return;
        }

        Sandwich temp = new Sandwich(bread, protein, qty);

        if (cheese.isSelected()) temp.addAddOn(AddOns.CHEESE);
        if (lettuce.isSelected()) temp.addAddOn(AddOns.LETTUCE);
        if (tomato.isSelected()) temp.addAddOn(AddOns.TOMATOES);
        if (onion.isSelected()) temp.addAddOn(AddOns.ONIONS);

        double p = temp.price();
        priceLabel.setText(String.format("$%.2f", p));
    }
    /**
     * Determines the selected protein based on the text of the chosen radio button.
     *
     * @return the selected {@link Protein}, or null if none is selected
     */
    private Protein getSelectedProtein() {
        Toggle t = proteinGroup.getSelectedToggle();
        if (t == null) return null;
        String text = ((RadioButton) t).getText().toLowerCase();
        if (text.contains("beef")) return Protein.BEEF;
        if (text.contains("chicken")) return Protein.CHICKEN;
        if (text.contains("salmon")) return Protein.SALMON;
        return null;
    }
    /**
     * Determines the selected bread based on the text of the chosen radio button.
     *
     * @return the selected {@link Bread}, or null if none is selected
     */
    private Bread getSelectedBread() {
        Toggle t = breadGroup.getSelectedToggle();
        if (t == null) return null;
        String text = ((RadioButton) t).getText().toLowerCase();
        if (text.contains("bagel")) return Bread.BAGEL;
        if (text.contains("wheat")) return Bread.WHEAT;
        if (text.contains("sourdough")) return Bread.SOURDOUGH;
        return null;
    }
    /**
     * Shows an informational alert dialog with the given title and message.
     *
     * @param title title for the alert
     * @param msg   message body for the alert
     */
    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
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
