package com.agriculture.marutisales;

import java.io.Serializable;

public class Product implements Serializable {
    String Name;
    String Price;
    String Description;

    public Product() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}



