package com.ivanboyukliev.plantsirrigationsystem.firebase.model;

public class FirebasePlantObj {

    private String plantName;
    private String imageURL;

    public FirebasePlantObj(){

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
