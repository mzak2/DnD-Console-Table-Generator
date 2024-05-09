package org.example.domain;

public class SubcategoryClass {
    public static class Subcategory{

        private int id;
        private String name;

        public Subcategory(int id, String name) {
        }

        public Subcategory(){

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

    }
}
