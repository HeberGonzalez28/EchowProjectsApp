package com.example.echowprojectsapp.Models;

import android.graphics.Bitmap;

public class integrantesItem {
    private Bitmap imageResId;
    private String itemName;
    private int id;

    public integrantesItem(Bitmap imageResId, String itemName, int id) {
        this.imageResId = imageResId;
        this.itemName = itemName;
        this.id = id;
    }

    public Bitmap getImageResId() {
        return imageResId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getId() {return id;}
}
