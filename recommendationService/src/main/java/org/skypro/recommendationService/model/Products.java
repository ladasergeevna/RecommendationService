package org.skypro.recommendationService.model;

public class Products {
    private String name;
    private String description;

    public Products(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Products() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Products{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
