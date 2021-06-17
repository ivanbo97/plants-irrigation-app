package com.ivanboyukliev.plantsirrigationsystem.navmenu.home;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ivanboyukliev.plantsirrigationsystem.searchedplantsrecyclerview.model.PlantFromApi;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<PlantFromApi> currentPlant;

    private MutableLiveData<String> plantFamily;

    public HomeViewModel() {
        currentPlant = new MutableLiveData<>();
        retrievePlantInfo();
    }

    public MutableLiveData<PlantFromApi> getPlantScientificName() {
        return currentPlant;
    }

    public MutableLiveData<String> getPlantFamily() {
        return plantFamily;
    }

    public void setPlantFamily(MutableLiveData<String> plantFamily) {
        this.plantFamily = plantFamily;
    }

    public MutableLiveData<PlantFromApi> getCurrentPlant() {
        return currentPlant;
    }

    public void retrievePlantInfo() {
        HomeFragment.getPlantApiRequest().getSinglePlantData(currentPlant);
    }
}