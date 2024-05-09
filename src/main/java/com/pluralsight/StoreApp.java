package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StoreApp {

    private static final String FILE_NAME = "products.csv";
    private static final ArrayList<Product> inventory = new ArrayList<>();
    private static final ArrayList<Product> cart = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in); // Create scanner to read user input

    public static void main(String[] args) {

        loadInventory(FILE_NAME);


        boolean running = true;

        double totalAmount = 0.0;

        // Display menu and get user choice until they choose to exit
        while (running) {
            System.out.println("Welcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Check out");
            System.out.println("4. Exit");

            String choice = scanner.nextLine().trim();

            // Call the appropriate method based on user choice
            switch (choice) {
                case "1":
                    displayProducts(inventory, cart, scanner);
                    break;
                case "2":
                    displayCart(cart, scanner, totalAmount);
                    break;
                case "3":
                    checkOut(cart, totalAmount);
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;

            }
        }
    }

    private static void loadInventory(String fileName) {

        try {

            // read from product file
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {

                String[] parts = line.split("\\|");

                // extract internal attributes from each line
                if (parts.length == 3) {

                    String id = parts[0];
                    String productName = parts[1];
                    double price = Double.parseDouble(parts[2]);

                    Product product = new Product(id, productName, price);

                    inventory.add(product); // add all products to the inventory list

                }

            }

            bufferedReader.close();


        } catch (FileNotFoundException e) {

            System.out.println("File not found! Please enter the correct file name " + e.getMessage());

        } catch (IOException e) {

            System.out.println("Error reading from product file" + e.getMessage());

        }

    }


    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        // This method should display a list of products from the inventory,
        // and prompt the user to add items to their cart. The method should
        // prompt the user to enter the ID of the product they want to add to their cart.
        // The method should add the selected product to the cart ArrayList.

        System.out.println("View Products: ");
        System.out.println("===========================================================================================");
        System.out.printf(" %-20s %-50s %s\n", "Product ID", "Product Name", "Product Price");
        System.out.println("===========================================================================================");


        for (Product product : inventory) {

            System.out.printf(" %-20s %-50s %.2f\n", product.getId(), product.getProductName(), product.getPrice());

        }

        System.out.print("\nEnter ID of the product you want to add to your cart: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter the name of the product you want to add to your cart: ");
        String name = scanner.nextLine().trim();


        // Find the product in the inventory by ID
        Product productToBeAdded = findProductById(id, inventory);

        // If the product is found, add it to the cart
        if (productToBeAdded != null) {

            cart.add(productToBeAdded);
            System.out.println("\nProduct added to cart: " + productToBeAdded.getProductName() + "\n");

        } else {

            System.out.println("\nProduct not found in inventory.\n");

        }


    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {
        // This method should display the items in the cart ArrayList, along with the total cost of all items in the cart.
        // The method should prompt the user to remove items from their cart by entering the ID of the product they want to remove.
        // The method should update the cart ArrayList and totalAmount variable accordingly.

        System.out.println("View Cart: ");
        System.out.println("===========================================================================================");
        System.out.printf(" %-20s %-50s %s\n", "Product ID", "Product Name", "Product Price");
        System.out.println("===========================================================================================");


        for (Product product : cart) {

            System.out.printf(" %-20s %-50s %.2f\n", product.getId(), product.getProductName(), product.getPrice());

            totalAmount += product.getPrice();

        }

        System.out.printf("\nSubtotal = $%.2f\n\n", totalAmount);

        System.out.print("Do you want to remove a product from your cart? (Y/N) ");
        String response = scanner.nextLine().trim();

        if (cart.isEmpty()) {

            System.out.println("\nOOPS!!! No product to remove.\n");

        } else {

            if (response.equalsIgnoreCase("Y")) {

                System.out.print("Enter product ID to remove product: ");
                String id = scanner.nextLine().trim();

                // Find the product in the inventory by ID
                Product productToBeRemoved = findProductById(id, inventory);

                // If the product is found, add it to the cart
                if (productToBeRemoved != null) {

                    cart.remove(productToBeRemoved);
                    System.out.println("\nProduct removed from cart: " + productToBeRemoved.getProductName() + "\n");

                } else {

                    System.out.println("\nProduct not found in the cart.\n");

                }


            } else if (response.equalsIgnoreCase("N")) {

                System.out.println("\nLet's continue! \n");

            } else {

                System.out.println("Invalid input. Try again.");

            }

        }

    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount) {
        // This method should calculate the total cost of all items in the cart, and display a summary of the purchase to the user.
        // The method should prompt the user to confirm the purchase, and deduct the total cost from their account if they confirm.

        // Check if the cart is empty
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Please add some items before checking out.");
            return;
        }

        // Display the summary of the purchase
        System.out.println("Summary of your purchase:");
        System.out.println("===========================================================================================");
        System.out.printf(" %-20s %-50s %s\n", "Product ID", "Product Name", "Product Price");
        System.out.println("===========================================================================================");

        for (Product product : cart) {

            System.out.printf(" %-20s %-50s %.2f\n", product.getId(), product.getProductName(), product.getPrice());
            totalAmount += product.getPrice();

        }

        System.out.printf("\nTotal Amount = $%.2f\n\n", totalAmount);

        // Prompt the user to confirm the purchase
        System.out.print("Do you want to proceed with the purchase? (Y/N) ");
        String response = scanner.nextLine().trim();

        if (response.equalsIgnoreCase("Y")) {

            // Deduct the total cost from the user's account (not implemented here)
            System.out.println("\nPayment successful. Thank you for your purchase!\n");

            // Clear the cart after successful purchase
            cart.clear();
            // Reset total amount to zero
            totalAmount = 0.0;

        } else if (response.equalsIgnoreCase("N")) {

            System.out.println("\nPurchase cancelled. Your cart has not been cleared.\n");

        } else {

            System.out.println("\nInvalid input. Please enter Y or N.\n");

        }


    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // This method should search the inventory ArrayList for a product with
        // the specified ID, and return the corresponding Product object. If
        // no product with the specified ID is found, the method should return null.

        for (Product product : inventory) {

            if (id.equalsIgnoreCase(product.getId())) {

                return product;
            }

        }

        return null;

    }

}