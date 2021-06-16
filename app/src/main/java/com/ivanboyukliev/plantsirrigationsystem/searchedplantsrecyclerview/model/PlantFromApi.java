package com.ivanboyukliev.plantsirrigationsystem.searchedplantsrecyclerview.model;

public class PlantFromApi {

    private String plantName;
    private String image_url;

    public PlantFromApi(String plantName, String image_url) {
        this.plantName = plantName;
        this.image_url = image_url;
    }


    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
