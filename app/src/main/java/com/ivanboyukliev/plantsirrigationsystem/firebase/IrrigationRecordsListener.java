package com.ivanboyukliev.plantsirrigationsystem.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseIrrigationRecord;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.irrigationhistory.IrrigationHistoryViewModel;

public class IrrigationRecordsListener implements ValueEventListener {

    @Override
    public void onDataChange(@NonNull DataSnapshot irrigationRecords) {
        for (DataSnapshot irrigationRecord : irrigationRecords.getChildren()) {
            String retrievedDate = irrigationRecord.child("/date").getValue(String.class);
            String retrievedStartTime = irrigationRecord.child("/startTime").getValue(String.class);
            String retrievedEndTime = irrigationRecord.child("/endTime").getValue(String.class);
            String retrievedMoistureLvl = String.valueOf(irrigationRecord.child("/moistureLvl").getValue(Long.class));

            FirebaseIrrigationRecord dataForIrrigationRecyclerView = new FirebaseIrrigationRecord(retrievedDate,
                    retrievedStartTime, retrievedEndTime, retrievedMoistureLvl);
            IrrigationHistoryViewModel.getIrrigationRecords().add(dataForIrrigationRecyclerView);
        }
        IrrigationHistoryViewModel.getIrrigationRecordsAdapter()
                .notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
