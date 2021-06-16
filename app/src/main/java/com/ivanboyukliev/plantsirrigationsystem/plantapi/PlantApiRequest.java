package com.ivanboyukliev.plantsirrigationsystem.plantapi;


import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebasePlantObj;

import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PLANT_API_PLANT_SEARCH_URL;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PLANT_API_TOKEN;

public class PlantApiRequest {

    private String plantName;
    private Context context;

    private RequestQueue requestQueue;
    private PlantApiResponseListener plantApiResponseListener;
    private PlantApiErrorListener plantApiErrorListener;

    public PlantApiRequest(String plantName, Context context, List<FirebasePlantObj> plants) {
        this.plantName = plantName;
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
        plantApiResponseListener = new PlantApiResponseListener(plants);
        plantApiErrorListener = new PlantApiErrorListener();
    }

    public void getPlants() {
        if (plantName == null) {
            Toast.makeText(context, "Please enter plant!!!", Toast.LENGTH_SHORT);
        }

        String url = PLANT_API_PLANT_SEARCH_URL + "?" + "token=" + PLANT_API_TOKEN + "&q=" + plantName;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, plantApiResponseListener, plantApiErrorListener);
        requestQueue.add(jsonObjectRequest);
    }
}
