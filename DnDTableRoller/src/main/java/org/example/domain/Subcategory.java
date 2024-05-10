package org.example.domain;

public class Subcategory {

    private int id;
    private int category_id;
    private String name;

    public Subcategory(int id, String name, int category_id) {
        this.id = id;
        this.name = name;
        this.category_id = category_id;
    }

    public Subcategory() {

    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getSubcategoryTable(Subcategory subcategory){

        String[] tableName = subcategory.getName().split("\\s+");
        return tableName[0].toLowerCase();
    }
}

