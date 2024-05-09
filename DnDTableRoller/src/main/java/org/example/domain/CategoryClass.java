package org.example.domain;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryClass {
    public class Category{

        private int category_id;
        private String category_name;
        private List<SubcategoryClass.Subcategory> subcategories;

        public Category(int category_id, String category_name, List<SubcategoryClass.Subcategory> subcategories) {
            this.category_id = category_id;
            this.category_name = category_name;
            this.subcategories = subcategories;
        }

        public Category(int category_id, String category_name) {
            this.category_id = category_id;
            this.category_name = category_name;
        }

        public Category() {

        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public List<SubcategoryClass.Subcategory> getSubcategories() {
            return subcategories;
        }

        public void setSubcategories(List<SubcategoryClass.Subcategory> subcategories) {
            this.subcategories = subcategories;
        }

        public void addSubcategory(Connection connection) throws SQLException {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM sub_categories WHERE category_id = " + category_id);

            if(!rs.next()){
                System.out.println("There is no subcategory that corresponds to this category");
            } else {
                do {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    subcategories.add(new SubcategoryClass.Subcategory(id, name));

                } while(rs.next());
            }

            statement.close();
        }
        public static List<String> getCategories(Connection connection) throws SQLException {
            List<String> categories = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM categories");

            if(!rs.next()){
                System.out.println("Could not find data in Categories!");
            } else {
                do {
                    String category = rs.getString("category_name");
                    categories.add(category);
                } while (rs.next());
            }

            return  categories;
        }
    }
}
