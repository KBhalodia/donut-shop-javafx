# RU Donuts – JavaFX Ordering System

A JavaFX-based point-of-sale (POS) application for managing donut shop orders.  
The system allows staff to create, customize, review, place, cancel, and export orders for donuts, coffee, and sandwiches through a graphical user interface.

This project was developed as the **final iteration of a multi-phase academic project**, with a focus on object-oriented design, MVC architecture, and GUI-based interaction.

---

## Features

- 🍩 **Donut Ordering**
  - Yeast, cake, donut holes, and seasonal donuts
  - Multiple flavors per donut type
  - Quantity selection and dynamic pricing

- ☕ **Coffee Customization**
  - Cup sizes: Short, Tall, Grande, Venti
  - Multiple add-ins (milk, caramel, mocha, etc.)
  - Live price updates as selections change

- 🥪 **Sandwich Builder**
  - Protein, bread, and add-on selections
  - Supports multiple add-ons with price adjustments

- 🧾 **Order Management**
  - Add/remove items from current order
  - Review order details before placing
  - Automatically generated unique order numbers
  - Cancel previously placed orders

- 📤 **Export Orders**
  - Save all placed orders to a text file
  - Includes item details and total pricing

---

## Design & Architecture

- **Language:** Java  
- **GUI Framework:** JavaFX  
- **Architecture:** MVC (Model–View–Controller)  
- **UI:** Multiple FXML views with dedicated controllers  
- **Testing:** JUnit tests for pricing logic  
- **Documentation:** Full Javadoc generation  

Key design principles used:
- Inheritance & polymorphism (`MenuItem` superclass)
- Dynamic binding
- Separation of concerns
- Event-driven programming

---

## Running the Project

### Requirements
- Java 17+ (or compatible JDK)
- JavaFX SDK

### Run
1. Clone the repository  
2. Open the project in IntelliJ IDEA  
3. Configure JavaFX in **VM options**:
   ```bash
   --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
