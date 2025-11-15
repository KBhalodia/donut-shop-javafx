package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Main;
import main.enums.Bread;
import main.enums.Protein;
import main.enums.AddOns;
import main.model.Sandwich;
import main.model.MenuItem;

public class SandwichController {

    @FXML private ToggleGroup proteinGroup; // beef, chicken, salmon
    @FXML private ToggleGroup breadGroup;   // bagel, wheat, sourdough
    @FXML private CheckBox cheese, lettuce, tomato, onion;
    @FXML private Spinner<Integer> qtySpinner;
    @FXML private Label priceLabel;
    @FXML private Button addBtn;

    @FXML
    private void initialize() {
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

    @FXML
    private void onSelectionChanged() {
        updatePrice();
    }

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

    private Protein getSelectedProtein() {
        Toggle t = proteinGroup.getSelectedToggle();
        if (t == null) return null;
        String text = ((RadioButton) t).getText().toLowerCase();
        if (text.contains("beef")) return Protein.BEEF;
        if (text.contains("chicken")) return Protein.CHICKEN;
        if (text.contains("salmon")) return Protein.SALMON;
        return null;
    }

    private Bread getSelectedBread() {
        Toggle t = breadGroup.getSelectedToggle();
        if (t == null) return null;
        String text = ((RadioButton) t).getText().toLowerCase();
        if (text.contains("bagel")) return Bread.BAGEL;
        if (text.contains("wheat")) return Bread.WHEAT;
        if (text.contains("sourdough")) return Bread.SOURDOUGH;
        return null;
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
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
