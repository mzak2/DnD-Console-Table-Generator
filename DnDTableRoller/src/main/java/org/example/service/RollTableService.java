package org.example.service;

import org.example.domain.Category;
import org.example.domain.Subcategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RollTableService {

    public static String rollTownEvent(Connection connection) throws SQLException{
        String event = rollTable(connection, "town_events", "description");
        String person_1 = rollTable(connection, "rng_npcs", "description");
        String person_2 = rollTable(connection, "rng_npcs", "description");

        StringBuilder sb = new StringBuilder();

        sb.append("While traveling through town, you come upon a:\n");
        sb.append(person_1);
        sb.append("\ncurrently "); //Having an argument, trading, telling
        sb.append(event);
        sb.append("\nwith a/an:\n");
        sb.append(person_2);

        System.out.println(sb);
        return sb.toString();
    }

    public static String rollPotions(Connection connection) throws SQLException {
        String effect = rollTable(connection, "potions", "description");
        String duration = rollTable(connection, "durations", "description");

        StringBuilder sb = new StringBuilder();
        sb.append("The Potion's effect is:\n");
        sb.append(effect);
        sb.append("\nWith a duration of:\n");
        sb.append(duration);

        System.out.println(sb);
        return sb.toString();
    }

    public static String rollMagicItems(Connection connection) throws SQLException{
        String description = rollTable(connection, "magic_items", "description");
        String equipmentType = rollTable(connection, "equipment", "name");

        StringBuilder sb = new StringBuilder();
        sb.append("You find a:\n");
        sb.append(equipmentType);
        sb.append("\nWith the effect of:\n");
        sb.append(description);

        System.out.println(sb);
        return sb.toString();
    }

    public static String rollCivilization(Connection connection, String table) throws SQLException {
        //description placeholders
        String terrain = "Location"; //rollTable(connection, "terrain", "description");
        String purpose = "Purpose in town";//rollTable(connection, "purpose", "description");
        String adjective = rollTable(connection, "adjectives", "name");

        //pulled from the respective wilderness table i.e. deserts
        String location_description = rollTable(connection, table, "description");
        String table_minus_s = table.substring(0, table.length() - 1);

        String item = rollTable(connection, "items", "name");
        String item_2 = rollTable(connection, "items", "name");
        String item_3 = rollTable(connection, "items", "name");
        String item_4 = rollTable(connection, "items", "name");
        String item_5 = rollTable(connection, "items", "name");

        //string output of the description
        StringBuilder sb = new StringBuilder();
        sb.append("While traveling through the ");
        sb.append(table_minus_s);
        sb.append(" they come upon a/an ");
        sb.append(adjective);
        sb.append(" ");
        sb.append(terrain);
        sb.append(", ");
        sb.append(location_description);
        sb.append(".\nIts purpose appears to be as a/an ");
        sb.append(purpose);
        sb.append(".\nInside is a/an... \n");
        sb.append(item).append(", a ").append(item_2).append(", a ").append(item_3).append(", a ").append(item_4).append(", and a ").append(item_5);


        //print to console or return the string (if needed in future)
        System.out.println(sb);
        return sb.toString();
    }

    public static String rollWilderness(Connection connection, String table) throws SQLException {
        //description placeholders
        String terrain = rollTable(connection, "terrain", "description");
        String purpose = rollTable(connection, "purpose", "description");
        String adjective = rollTable(connection, "adjectives", "name");

        //pulled from the respective wilderness table i.e. deserts
        String location_description = rollTable(connection, table, "description");
        String table_minus_s = table.substring(0, table.length() - 1);

        String item = rollTable(connection, "items", "name");
        String item_2 = rollTable(connection, "items", "name");
        String item_3 = rollTable(connection, "items", "name");
        String item_4 = rollTable(connection, "items", "name");
        String item_5 = rollTable(connection, "items", "name");

        //string output of the description
        StringBuilder sb = new StringBuilder();
        sb.append("While traveling through the ");
        sb.append(table_minus_s);
        sb.append(" they come upon a/an ");
        sb.append(adjective);
        sb.append(" ");
        sb.append(terrain);
        sb.append(", ");
        sb.append(location_description);
        sb.append(".\nIts purpose appears to be as a/an ");
        sb.append(purpose);
        sb.append(".\nInside is a/an... \n");
        sb.append(item).append(", a ").append(item_2).append(", a ").append(item_3).append(", a ").append(item_4).append(", and a ").append(item_5);


        //print to console or return the string (if needed in future)
        System.out.println(sb);
        return sb.toString();
    }

    public static String rollTable(Connection connection, String table, String column) throws SQLException {
        int maxTableId = getMaxId(connection, table, "id");
        String result = "";

        Random random = new Random();
        int roll = random.nextInt(1, maxTableId);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT " + column + " FROM " + table + " WHERE id = " + roll);

        if(!rs.next()){
            System.out.println("The roll result was out of bounds of the table id");
        } else {
            do{
                result = rs.getString(column);
                System.out.println(table + ": " + result);
            } while (rs.next());
        }

        statement.close();
        return result;
    }

    private static int getMaxId(Connection connection, String table, String idColumn) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(" + idColumn + ") FROM " + table);
        int maxId = 0;
        if(rs.next()){
            maxId = rs.getInt(1);
        }

        statement.close();
        return maxId + 1;
    }

    public static List<Category> getCategories(Connection connection) throws SQLException {
        List<Category> categories = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM categories");

        if(!rs.next()){
            System.out.println("Could not find data in Categories!");
        } else {
            do {
                int category_id = rs.getInt("category_id");
                String category_name = rs.getString("category_name");
                Category category = new Category(category_id, category_name);
                category.addSubcategory(connection);
                categories.add(category);
            } while (rs.next());
        }

        return  categories;
    }

    public static Category getCategory(Connection connection, int categoryId) throws  SQLException{
        Category category = null;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM categories WHERE category_id = " + categoryId);

        if(!rs.next()){
            System.out.println("There is no category matching " + categoryId);
        } else {
            do {
                int category_id = rs.getInt("category_id");
                String category_name = rs.getString("category_name");
                category = new Category(category_id, category_name);
                category.addSubcategory(connection);
            } while (rs.next());
        }

        return category;
    }

    public static Subcategory getSubcategory(Connection connection, int subcategoryId) throws SQLException {
        Subcategory subcategory = null;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM sub_categories WHERE id = " + subcategoryId);

        if(!rs.next()){
            System.out.println("There is no subcategory that matches this id: " + subcategoryId);
        } else {
            String sub_name = rs.getString("name");
            int sub_id = rs.getInt("id");
            int cat_id = rs.getInt("category_id");
            subcategory = new Subcategory(sub_id, sub_name, cat_id);
        }

        statement.close();
        return subcategory;
    }
}
