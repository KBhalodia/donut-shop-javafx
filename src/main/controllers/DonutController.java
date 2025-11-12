package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DonutController {

    @FXML private ComboBox<String> donutTypeCombo;   // yeast, cake, holes, seasonal
    @FXML private ListView<String> flavorList;       // populate based on type
    @FXML private Spinner<Integer> qtySpinner;
    @FXML private Label priceLabel;
    @FXML private ImageView itemImage;
    @FXML private Button addBtn;

    @FXML
    private void initialize() {
        // TODO: populate donutTypeCombo with types
        // TODO: set qtySpinner value factory (min=1)
        // TODO: bind listeners: on type change -> reload flavors + image; on selection -> updatePrice()
        updatePrice();
    }

    @FXML
    private void onTypeChanged() {
        // TODO: switch flavors + image based on selected type
        // itemImage.setImage(new Image(...));
        updatePrice();
    }

    @FXML
    private void onFlavorSelected() {
        updatePrice();
    }

    @FXML
    private void onQtyChanged() {
        updatePrice();
    }

    @FXML
    private void onAddToOrder() {
        // TODO: build a Donut (your model) with selected type, flavor, qty; add to CURRENT order
        // App state handoff goes here.
        clearSelections();
    }

    private void updatePrice() {
        // TODO: compute running price using your Donut.price()
        priceLabel.setText("$0.00");
    }

    private void clearSelections() {
        flavorList.getSelectionModel().clearSelection();
        qtySpinner.getValueFactory().setValue(1);
    }
}
