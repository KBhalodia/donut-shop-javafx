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
            "Spooky Donut", "Pumpkin Spice"
    };

    @FXML
    private void initialize() {
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

    // Called by FXML on type combo change
    @FXML
    private void onTypeChanged() {
        loadFlavorsForCurrentType();
        updateImage();
        updatePrice();
    }

    // Called when a flavor is clicked
    @FXML
    private void onFlavorSelected() {
        updatePrice();
    }

    // Called when qty spinner changes
    @FXML
    private void onQtyChanged() {
        updatePrice();
    }

    // Clear button handler (we’ll point FXML to this)
    @FXML
    private void onClear() {
        donutTypeCombo.getSelectionModel().selectFirst();
        loadFlavorsForCurrentType();
        flavorList.getSelectionModel().clearSelection();
        qtySpinner.getValueFactory().setValue(1);
        updateImage();
        updatePrice();
    }

    // Add selected donut to current order
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

    /** Reload flavors based on the currently selected type string */
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

    /** Map combo text → DonutCategory enum used in your Donut model */
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

    /** Update image based on selected type (use your real file names if you have them) */
    private void updateImage() {
        DonutCategory type = getSelectedType();
        if (type == null) {
            itemImage.setImage(null);
            return;
        }
        // Placeholder paths – put your actual images in resources and use correct names
        String path = switch (type) {
            case YEAST -> "/images/yeast.png";
            case CAKE -> "/images/cake.png";
            case HOLE -> "/images/holes.png";
            case SEASONAL -> "/images/seasonal.png";
        };
        try {
            itemImage.setImage(new Image(getClass().getResourceAsStream(path)));
        } catch (Exception e) {
            // If image missing, just ignore
            itemImage.setImage(null);
        }
    }

    /** Live price preview using your Donut.price() logic */
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

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    @FXML
    private void onBackToMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main-menu.fxml"));
            Stage stage = (Stage) donutTypeCombo.getScene().getWindow();
            stage.setTitle("RU Donuts - Main Menu");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            stage.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
