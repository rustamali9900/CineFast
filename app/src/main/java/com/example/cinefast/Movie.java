package com.example.cinefast;

public class Movie {
    private String title;
    private String details;
    private int imageResId;
    private String trailerQuery;

    public Movie(String title, String details, int imageResId, String trailerQuery) {
        this.title = title;
        this.details = details;
        this.imageResId = imageResId;
        this.trailerQuery = trailerQuery;
    }

    public String getTitle() { return title; }
    public String getDetails() { return details; }
    public int getImageResId() { return imageResId; }
    public String getTrailerQuery() { return trailerQuery; }
}