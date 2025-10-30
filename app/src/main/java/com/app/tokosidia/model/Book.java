package com.app.tokosidia.model;

public class Book {

    private String title;
    private String author;
    private double price;
    private String description;
    private float rating;
    private int imageResId;

    // Konstruktor disesuaikan untuk menerima 'author'
    public Book(String title, String author, double price, String description, float rating, int imageResId) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.rating = rating;
        this.imageResId = imageResId;
    }

    // Getter method
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public float getRating() { return rating; }
    public int getImageResId() { return imageResId; }
}