package com.ivanboyukliev.plantsirrigationsystem.plantapi;

import android.util.Log;

import com.android.volley.Response;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.MqttBrokerPlantRegDialog;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebasePlantObj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PLANT_NO_IMAGE_URL;

public class PlantApiResponseListener implements Response.Listener<JSONObject> {

    private List<FirebasePlantObj> plants;

    public PlantApiResponseListener(List<FirebasePlantObj> plants) {
        this.plants = plants;
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
                String plantNameAndId = jsonObject.getString("common_name");
                String plantId = jsonObject.getString("id");
                if (plantNameAndId.compareTo("null") == 0)
                    continue;
                String plantImageUrl;
                try {
                    plantImageUrl = jsonObject.getString("image_url");
                } catch (JSONException jsonException) {
                    plantImageUrl = PLANT_NO_IMAGE_URL;
                }
                plants.add(new FirebasePlantObj(plantNameAndId, plantImageUrl,plantId));
            }

            MqttBrokerPlantRegDialog.getRetrievedPlantsAdapter().notifyDataSetChanged();
            Log.i("RETRIEVED DATA:", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}