package com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata.utils;

import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata.RealtimeDataFragment;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DB_URL;

public class LiveDataChecker implements Runnable {

    private TextView seasonTv;
    private TextView temperatureTv;
    private TextView plantNameTv;
    private RealtimeDataFragment realtimeDataFragment;

    public LiveDataChecker(TextView seasonTv, TextView temperatureTv, TextView plantNameTv, RealtimeDataFragment realtimeDataFragment) {
        this.seasonTv = seasonTv;
        this.temperatureTv = temperatureTv;
        this.plantNameTv = plantNameTv;
        this.realtimeDataFragment = realtimeDataFragment;
    }

    @Override
    public void run() {

        String currentSeason = seasonTv.getText().toString().trim();
        String currentTemperature = temperatureTv.getText().toString();
        String plantName = plantNameTv.getText().toString();

        if (currentSeason.equals("") || currentTemperature.equals("")) {
            return;
        }

        DatabaseReference growingHints = FirebaseDatabase.getInstance(DB_URL)
                .getReference("growingHints/" +
                        plantName + '/' + currentSeason + '/' +
                        "tempRange");

        growingHints.addValueEventListener(new TempRangeDataListener(realtimeDataFragment));
    }
}
