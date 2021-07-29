package com.ivanboyukliev.plantsirrigationsystem.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseIrrigationRecord;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.irrigationhistory.IrrigationHistoryViewModel;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.irrigationhistory.irrigationsrecyclerview.adapter.IrrigationRecordsListAdapter;

import java.util.List;

public class IrrigationRecordsListener implements ValueEventListener {

    @Override
    public void onDataChange(@NonNull DataSnapshot irrigationRecords) {
        List<FirebaseIrrigationRecord> irrigationHistoryList = IrrigationHistoryViewModel.getIrrigationRecords();
        IrrigationRecordsListAdapter irrigationRecordsListAdapter = IrrigationHistoryViewModel.getIrrigationRecordsAdapter();
        for (DataSnapshot irrigationRecord : irrigationRecords.getChildren()) {
            String retrievedDate = irrigationRecord.child("/date").getValue(String.class);
            String retrievedStartTime = irrigationRecord.child("/startTime").getValue(String.class);
            String retrievedEndTime = irrigationRecord.child("/endTime").getValue(String.class);
            String retrievedMoistureLvl = irrigationRecord.child("/moistureLvl").getValue(String.class);

            FirebaseIrrigationRecord dataForIrrigationRecyclerView = new FirebaseIrrigationRecord(retrievedDate,
                    retrievedStartTime, retrievedEndTime, retrievedMoistureLvl);
            irrigationHistoryList.add(dataForIrrigationRecyclerView);
            irrigationRecordsListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
