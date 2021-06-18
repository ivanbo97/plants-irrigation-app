package com.ivanboyukliev.plantsirrigationsystem.firebase.model;

import java.io.Serializable;

public class FirebasePlantObj implements Serializable {

    private String plantName;
    private String imageURL;

    public FirebasePlantObj() {

    }

    public FirebasePlantObj(String plantName, String imageURL) {
        this.plantName = plantName;
        this.imageURL = imageURL;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
