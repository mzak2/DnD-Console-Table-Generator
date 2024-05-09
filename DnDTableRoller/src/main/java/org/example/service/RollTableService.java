package org.example.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class RollTableService {

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
        //return sb.toString();
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
                //System.out.println("Description: " + result);
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
}
