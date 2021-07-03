package com.ivanboyukliev.plantsirrigationsystem.plantapi;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.ivanboyukliev.plantsirrigationsystem.searchedplantsrecyclerview.model.PlantFromApi;

import org.json.JSONException;
import org.json.JSONObject;


import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PLANT_NO_IMAGE_URL;

public class SinglePlantDataResponseListener implements Response.Listener<JSONObject> {

    private MutableLiveData<PlantFromApi> plantFromApiDataObj;

    public SinglePlantDataResponseListener(MutableLiveData<PlantFromApi> plant) {
        this.plantFromApiDataObj = plant;
    }

    @Override
    public void onResponse(JSONObject response) {
        try {

            String plantScientificName = response.getString("scientific_name");

            String plantCommonName = response.getString("common_name");

            String plantFamily = response.getString("family");

            String plantImageUrl;

            try {
                plantImageUrl = response.getString("image_url");
            } catch (JSONException jsonException) {
                plantImageUrl = PLANT_NO_IMAGE_URL;
            }

            PlantFromApi plantFromApi = new PlantFromApi(plantCommonName, plantImageUrl, plantScientificName, plantFamily);
            plantFromApiDataObj.setValue(plantFromApi);
            Log.i("RETRIEVED DATA:", response.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
