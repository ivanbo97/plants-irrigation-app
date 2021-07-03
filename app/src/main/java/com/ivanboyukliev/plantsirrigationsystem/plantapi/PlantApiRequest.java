package com.ivanboyukliev.plantsirrigationsystem.plantapi;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebasePlantObj;
import com.ivanboyukliev.plantsirrigationsystem.searchedplantsrecyclerview.model.PlantFromApi;

import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PLANT_API_PLANT_DATA_URL;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PLANT_API_PLANT_SEARCH_URL;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PLANT_API_TOKEN;
import static com.ivanboyukliev.plantsirrigationsystem.utils.FirebaseRetrievedDataConverter.extractIdFromPlantName;

public class PlantApiRequest {

    private String plantName;
    private Context context;

    private RequestQueue requestQueue;
    private PlantApiErrorListener plantApiErrorListener;

    public PlantApiRequest(String plantName, Context context) {
        this.plantName = plantName;
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
        plantApiErrorListener = new PlantApiErrorListener();
    }

    public void getPlants(List<FirebasePlantObj> plants) {
        if (plantName == null) {
            Toast.makeText(context, "Please enter plant!!!", Toast.LENGTH_SHORT);
            return;
        }
        PlantApiResponseListener plantApiResponseListener = new PlantApiResponseListener(plants);
        String url = PLANT_API_PLANT_SEARCH_URL + "?" + "token=" + PLANT_API_TOKEN + "&q=" + plantName;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, plantApiResponseListener, plantApiErrorListener);
        requestQueue.add(jsonObjectRequest);
    }

    public void getSinglePlantData(MutableLiveData<PlantFromApi> plant) {
        SinglePlantDataResponseListener singlePlantDataResponseListener = new SinglePlantDataResponseListener(plant);
        String plantApiId = extractIdFromPlantName(plantName);
        String url = PLANT_API_PLANT_DATA_URL + plantApiId + "?" + "token=" + PLANT_API_TOKEN;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, singlePlantDataResponseListener, plantApiErrorListener);
        requestQueue.add(jsonObjectRequest);
    }
}
