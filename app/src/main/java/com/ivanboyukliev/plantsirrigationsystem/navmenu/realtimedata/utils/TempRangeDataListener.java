package com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata.utils;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata.RealtimeDataFragment;

public class TempRangeDataListener implements ValueEventListener {

    private RealtimeDataFragment realtimeDataFragment;

    public TempRangeDataListener(RealtimeDataFragment realtimeDataFragment) {
        this.realtimeDataFragment = realtimeDataFragment;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot tempRange : snapshot.getChildren()) {
            String[] tempIntervals = tempRange.getKey().split("-");
            int minTemperature = Integer.parseInt(tempIntervals[0]);
            int maxTemperature = Integer.parseInt(tempIntervals[1]);
            realtimeDataFragment.getTempRanges().add(new TempRange(minTemperature, maxTemperature));
        }
        realtimeDataFragment.onTemperatureRangesExtracted();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        HomeActivity.showBrokerMessage(error.getMessage());
    }
}
