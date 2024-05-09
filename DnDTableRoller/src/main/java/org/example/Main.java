package org.example;


import java.sql.*;
import java.util.Scanner;

import static org.example.domain.CategoryClass.Category.getCategories;
import static org.example.service.RollTableService.rollTable;
import static org.example.service.RollTableService.rollWilderness;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);


        //-----------Database connection
        String DB_URL = System.getenv("DB_URL");
        String USER = System.getenv("DB_USER");
        String PASS = System.getenv("DB_PASS");

        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        //test comment
        //------------START PROGRAM-------------
        //TODO fix placeholder for category list to be selectable
        int count = 0;
        for (String category : getCategories(connection)){
            count++;
            System.out.println(count + ". " + category);
        }

        while (true){
            //TODO add logic to give options for every subcategory
            System.out.println("What table would you like to roll on?");
            System.out.println("1. Deserts");
            //TODO add logic to roll tables like Items without printing to console
            System.out.println("2. Items");
            System.out.println("3. Exit");
            String user_input = scanner.nextLine();
            if(user_input.toLowerCase().trim().equals("1") || user_input.toLowerCase().trim().equals("1.")){
                System.out.println();
                rollWilderness(connection, "deserts");
                System.out.println();
            } else if(user_input.toLowerCase().trim().equals("2") || user_input.toLowerCase().trim().equals("2.")) {
                rollTable(connection, "items", "name");
            } else if(user_input.toLowerCase().trim().equals("3") || user_input.toLowerCase().trim().equals("3.") || user_input.toLowerCase().trim().equals("exit")) {
                break;
            } else {
                System.out.println("Please select from a valid option");
            }
        }


        connection.close();
    }

}