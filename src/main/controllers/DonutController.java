package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import main.enums.DonutCategory;
import main.model.Donut;
import main.model.MenuItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller for the donut ordering view.
 * Allows the user to select a donut category, flavor, quantity,
 * and view a live price preview before adding the donut to the current order.
 */
public class DonutController {

    @FXML private ComboBox<String> donutTypeCombo;   // yeast, cake, holes, seasonal
    @FXML private ListView<String> flavorList;       // populate based on type
    @FXML private Spinner<Integer> qtySpinner;
    @FXML private Label priceLabel;
    @FXML private ImageView itemImage;
    @FXML private Button addBtn;

    // Simple flavor sets – adjust names if your prof gave specific ones
    private static final String[] YEAST_FLAVORS = {
            "Glazed", "Chocolate", "Strawberry", "Sugar", "Boston Creme", "Jelly"
    };
    private static final String[] CAKE_FLAVORS = {
            "Old Fashioned", "Blueberry", "Cinnamon"
    };
    private static final String[] HOLE_FLAVORS = {
            "Glazed Holes", "Powdered Holes", "Cinnamon Holes"
    };
    private static final String[] SEASONAL_FLAVORS = {
            "Spooky Donuts", "Pumpkin Spice Donuts"
    };
    /**
     * Initializes the donut ordering view.
     * Sets up the donut type combo box, quantity spinner, selection listeners,
     * and loads the initial flavors, image, and price.
     */
    @FXML
    private void initialize() {
        // Set window title
        if (donutTypeCombo != null && donutTypeCombo.getScene() != null) {
            Stage stage = (Stage) donutTypeCombo.getScene().getWindow();
            if (stage != null) {
                stage.setTitle("Ordering Donuts");
            }
        }
        
        // Fill donut type combo
        donutTypeCombo.getItems().addAll("Yeast", "Cake", "Donut Holes", "Seasonal");
        donutTypeCombo.getSelectionModel().selectFirst();

        // Quantity spinner 1–12
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1);
        qtySpinner.setValueFactory(valueFactory);

        // When type changes, reload flavors + image + price
        donutTypeCombo.setOnAction(e -> onTypeChanged());
        qtySpinner.valueProperty().addListener((obs, o, n) -> onQtyChanged());
        flavorList.getSelectionModel().selectedItemProperty().addListener(
                (obs, o, n) -> onFlavorSelected()
        );

        // Load initial state
        loadFlavorsForCurrentType();
        updateImage();
        updatePrice();
    }

    /**
     * Handles changes to the selected donut type in the combo box.
     * Reloads the flavor list, updates the image, and recomputes the price.
     */
    @FXML
    private void onTypeChanged() {
        loadFlavorsForCurrentType();
        updateImage();
        updatePrice();
    }

    /**
     * Handles changes to the selected flavor in the flavor list.
     * Recomputes the price using the new selection.
     */
    @FXML
    private void onFlavorSelected() {
        updatePrice();
    }

    /**
     * Handles changes to the quantity spinner value.
     * Recomputes the price using the new quantity.
     */
    @FXML
    private void onQtyChanged() {
        updatePrice();
    }

    /**
     * Clears the current donut selection by resetting the type, flavors,
     * quantity, image, and price to their default values.
     */
    @FXML
    private void onClear() {
        donutTypeCombo.getSelectionModel().selectFirst();
        loadFlavorsForCurrentType();
        flavorList.getSelectionModel().clearSelection();
        qtySpinner.getValueFactory().setValue(1);
        updateImage();
        updatePrice();
    }

    /**
     * Adds the currently selected donut (category, flavor, and quantity)
     * to the shared current order, updates the current order view if open,
     * and shows a confirmation alert.
     */
    @FXML
    private void onAddToOrder() {
        DonutCategory type = getSelectedType();
        String flavor = flavorList.getSelectionModel().getSelectedItem();
        Integer qty = qtySpinner.getValue();

        if (type == null || flavor == null || qty == null) {
            showAlert("Missing Selection",
                    "Please select a donut type, a flavor, and a quantity.");
            return;
        }

        Donut donut = new Donut(type, flavor, qty);
        Main.currentOrder.addItem(donut);

        if (Main.currentOrderController != null) {
            Main.currentOrderController.refreshTotals();
        }

        showAlert("Donut Added",
                String.format("%s x%d added to current order.", flavor, qty));

        onClear();
    }

    /**
     * Reloads the flavor list based on the currently selected donut type.
     * If there are flavors available, the first flavor is automatically selected.
     */
    private void loadFlavorsForCurrentType() {
        flavorList.getItems().clear();
        DonutCategory type = getSelectedType();
        if (type == null) {
            return;
        }
        switch (type) {
            case YEAST -> flavorList.getItems().addAll(YEAST_FLAVORS);
            case CAKE -> flavorList.getItems().addAll(CAKE_FLAVORS);
            case HOLE -> flavorList.getItems().addAll(HOLE_FLAVORS);
            case SEASONAL -> flavorList.getItems().addAll(SEASONAL_FLAVORS);
        }
        if (!flavorList.getItems().isEmpty()) {
            flavorList.getSelectionModel().selectFirst();
        }
    }

    /**
     * Converts the currently selected text in the donut type combo box
     * into the corresponding {@link DonutCategory} enum value.
     *
     * @return the selected donut category, or {@code null} if none is selected
     */
    private DonutCategory getSelectedType() {
        String s = donutTypeCombo.getSelectionModel().getSelectedItem();
        if (s == null) return null;
        return switch (s) {
            case "Yeast" -> DonutCategory.YEAST;
            case "Cake" -> DonutCategory.CAKE;
            case "Donut Holes" -> DonutCategory.HOLE;
            case "Seasonal" -> DonutCategory.SEASONAL;
            default -> null;
        };
    }

    /**
     * Updates the donut image preview based on the currently selected donut type.
     * Currently all donut types use the same image resource.
     * If the image cannot be loaded, the image view is cleared.
     */
    private void updateImage() {
        DonutCategory type = getSelectedType();
        if (type == null) {
            // Load default donuts image
            try {
                itemImage.setImage(new Image(getClass().getResourceAsStream("/images/donuts.jpg")));
            } catch (Exception e) {
                itemImage.setImage(null);
            }
            return;
        }
        // Use the donuts.jpg image for all donut types
        try {
            itemImage.setImage(new Image(getClass().getResourceAsStream("/images/donuts.jpg")));
        } catch (Exception e) {
            // If image missing, just ignore
            itemImage.setImage(null);
        }
    }

    /**
     * Computes and displays the current price of the selected donut order
     * using the {@link Donut#price()} method. If the selection is incomplete,
     * the price label is reset to $0.00.
     */
    private void updatePrice() {
        DonutCategory type = getSelectedType();
        String flavor = flavorList.getSelectionModel().getSelectedItem();
        Integer qty = qtySpinner.getValue();

        if (type == null || flavor == null || qty == null) {
            priceLabel.setText("Price: $0.00");
            return;
        }
        Donut temp = new Donut(type, flavor, qty);
        double p = temp.price();
        priceLabel.setText(String.format("Price: $%.2f", p));
    }
    /**
     * Displays an informational alert dialog with the given title and message.
     *
     * @param title the title to show in the alert window
     * @param msg   the message text to display in the alert
     */
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    /**
     * Handles navigation back to the main view (main-view.fxml) from the donut view.
     * This version uses the event source to preserve the existing window size and styling.
     *
     * @param event the action event generated by clicking the back button
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
