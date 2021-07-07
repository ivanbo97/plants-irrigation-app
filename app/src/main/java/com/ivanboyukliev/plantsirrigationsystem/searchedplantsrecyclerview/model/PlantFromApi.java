package com.ivanboyukliev.plantsirrigationsystem.searchedplantsrecyclerview.model;

public class PlantFromApi {

    private String plantName;
    private String imageUrl;
    private String scientificName;
    private String family;

    public PlantFromApi (){

    }

    public PlantFromApi(String plantName, String imageUrl, String scientificName, String family) {
        this.plantName = plantName;
        this.imageUrl = imageUrl;
        this.scientificName = scientificName;
        this.family = family;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
