package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import main.model.StoreOrders;
import main.model.Order;

/**
 * The entry point for the RU Cafe application.
 * <p>
 * This class initializes the primary JavaFX stage, loads the main menu view,
 * applies the application stylesheet, and maintains shared application-wide
 * state such as the current order and all store orders.
 * </p>
 * <p>
 * The {@code storeOrders} and {@code currentOrder} objects are shared across
 * controllers to allow seamless access when placing, viewing, or exporting orders.
 * </p>
 */
public class Main extends Application {
    /** Stores all completed orders placed during the application's runtime. */
    public static StoreOrders storeOrders = new StoreOrders();
    /** Represents the active order that the user is currently building. */
    public static Order currentOrder = new Order();
    /** Controller reference for the Current Order screen (set dynamically). */
    public static main.controllers.CurrentOrderController currentOrderController;
    /** Controller reference for the Store Orders screen (set dynamically). */
    public static main.controllers.AllOrdersController allOrdersController;
    /**
     * Starts the JavaFX application by loading the main menu view,
     * setting the initial window size, applying stylesheets,
     * and displaying the primary stage.
     *
     * @param stage the main application window provided by the JavaFX runtime
     * @throws Exception if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main-view.fxml"));
        Scene scene = new Scene(root, 900, 700); // Set initial size
        scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
        stage.setTitle("RU Cafe");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
