package com.example.cinefast;

public class Snack {
    private String id; 
    private String name;
    private String description;
    private int imageResId;
    private int price;
    private int quantity;

    public Snack(String id, String name, String description, int imageResId, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageResId = imageResId;
        this.price = price;
        this.quantity = 0; // Default quantity is 0
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}