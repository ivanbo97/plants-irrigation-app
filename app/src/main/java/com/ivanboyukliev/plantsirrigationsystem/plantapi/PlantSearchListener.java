package com.ivanboyukliev.plantsirrigationsystem.plantapi;

import android.content.Context;
import android.widget.SearchView;

import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebasePlantObj;

import java.util.List;


public class PlantSearchListener implements SearchView.OnQueryTextListener {

    private List<FirebasePlantObj> retrievedPlants;
    private Context uiFragmentContext;

    public PlantSearchListener(List<FirebasePlantObj> plantsList, Context context) {
        this.retrievedPlants = plantsList;
        this.uiFragmentContext = context;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        retrievePlantsFromApi(query, uiFragmentContext);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void retrievePlantsFromApi(String plantForSearch, Context context) {
        PlantApiRequest apiRequest = new PlantApiRequest(plantForSearch, context);
        retrievedPlants.clear();
        apiRequest.getPlants(retrievedPlants);
    }
}
