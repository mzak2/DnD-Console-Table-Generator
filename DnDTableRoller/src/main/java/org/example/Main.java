package org.example;


import org.example.domain.Category;
import org.example.domain.Subcategory;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

import static org.example.domain.Subcategory.getSubcategoryTable;
import static org.example.service.RollTableService.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);


        //-----------Database connection
        String DB_URL = System.getenv("DB_URL");
        String USER = System.getenv("DB_USER");
        String PASS = System.getenv("DB_PASS");

        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        //------------START PROGRAM-------------
        // Populate our subcategories to our categories
        for(Category category : getCategories(connection)){
            if(category.getSubcategories() == null || category.getSubcategories().isEmpty()) {
                category.addSubcategory(connection);
            }
        }

        while (true) {
            //TODO add logic to give options for every subcategory
            //TODO add logic to roll tables like Items without printing to console
            // Print categories
            System.out.println("What Category would you like to select?");
            for (Category category : getCategories(connection)){
                System.out.println(category.getCategory_id() + ". " + category.getCategory_name());
            }
            System.out.println("100. Exit");

            String user_input = scanner.nextLine();
            int user_num = 0;
            Category categoryChosen = null;

            try {
                user_num = Integer.parseInt(user_input);
            } catch (NumberFormatException e){
                user_num = 0;
                e.printStackTrace();
            }

            if (user_num > 0 && user_num != 100) {
                categoryChosen = getCategory(connection, user_num);

                // Loop for subcategories
                while (true) {
                    System.out.println("You selected " + categoryChosen.getCategory_name() + "\n Please Select from the following tables:");
                    List<Subcategory> subcategories = categoryChosen.getSubcategories();
                    for (Subcategory subcategory : subcategories) {
                        System.out.println("\t" + subcategory.getId() + ". " + subcategory.getName());
                    }
                    System.out.println("100. Back");

                    // Ask user which subcategory they want
                    user_input = scanner.nextLine();

                    try {
                        user_num = Integer.parseInt(user_input);
                    } catch (NumberFormatException e){
                        user_num = 0;
                        e.printStackTrace();
                    }

                    if (user_num > 0 && user_num != 100) {
                        Subcategory subcategory = getSubcategory(connection, user_num);
                        String chosenTable = getSubcategoryTable(subcategory);

                        if (subcategory.getCategory_id() == 2) {
                            rollWilderness(connection, chosenTable);
                        } else if (chosenTable.equals("durations") || chosenTable.equals("magic_items")) {
                            rollTable(connection, chosenTable, "description");
                        } else {
                            rollTable(connection, chosenTable, "name");
                        }
                    } else if (user_input.toLowerCase().trim().equals("100") || user_input.toLowerCase().trim().equals("back")) {
                        break; // Go back to the category selection
                    } else {
                        System.out.println("Please enter a valid option");
                    }
                }
            } else if (user_input.toLowerCase().trim().equals("100") || user_input.toLowerCase().trim().equals("exit")) {
                break; // Exit the program
            } else {
                System.out.println("Please select from a valid option");
            }
        }



        connection.close();
    }

}