package com.ivanboyukliev.plantsirrigationsystem.plantapi;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.ivanboyukliev.plantsirrigationsystem.searchedplantsrecyclerview.model.PlantFromApi;

import org.json.JSONArray;
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
            JSONArray jsonArray = response.getJSONArray("data");
            if (jsonArray.length() == 0) {
                Log.d("ARRAY SIZE: ", "Size is 0");
                return;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String plantScientificName = jsonObject.getString("scientific_name");
                if (plantScientificName.compareTo("null") == 0)
                    continue;

                String plantCommonName = jsonObject.getString("common_name");
                if (plantCommonName.compareTo("null") == 0)
                    continue;

                String plantFamily = jsonObject.getString("family");
                if (plantFamily.compareTo("null") == 0)
                    continue;

                String plantImageUrl;
                try {
                    plantImageUrl = jsonObject.getString("image_url");
                } catch (JSONException jsonException) {
                    plantImageUrl = PLANT_NO_IMAGE_URL;
                }

                PlantFromApi plantFromApi = new PlantFromApi(plantCommonName, plantImageUrl, plantScientificName, plantFamily);

                plantFromApiDataObj.setValue(plantFromApi);
            }

            Log.i("RETRIEVED DATA:", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
