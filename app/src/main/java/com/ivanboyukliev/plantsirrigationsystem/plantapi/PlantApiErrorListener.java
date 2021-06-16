package com.ivanboyukliev.plantsirrigationsystem.plantapi;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class PlantApiErrorListener implements Response.ErrorListener {

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("ERROR:", " VOLLEY ERROR");
        error.printStackTrace();
    }
}
