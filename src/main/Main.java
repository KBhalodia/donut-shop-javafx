package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import main.model.StoreOrders;
import main.model.Order;

/**
 * Launches the RU Donut JavaFX application.
 */
public class Main extends Application {

    public static StoreOrders storeOrders = new StoreOrders();
    public static Order currentOrder = new Order();
    public static main.controllers.CurrentOrderController currentOrderController;
    public static main.controllers.AllOrdersController allOrdersController;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main-view.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
        stage.setTitle("RU Cafe");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
