package com.myapplicationdev.android.OurSingapore;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Island implements Serializable {

	private int id;
	private String name;
	private String description;
	private int area;
	private int stars;

    public Island(int id, String name, String description, int area, int stars) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.area = area;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public Island setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Island setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() { return description; }

    public Island setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getArea() {
        return area;
    }

    public Island setArea(int area) {
        this.area = area;
        return this;
    }

    public int getStars() {
        return stars;
    }

    public Island setStars(int stars) {
        this.stars = stars;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        String starsString = "";
        for(int i = 0; i < stars; i++){
            starsString += "* ";
        }
        return starsString;

    }

}
