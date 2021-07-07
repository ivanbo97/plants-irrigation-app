package com.ivanboyukliev.plantsirrigationsystem.navmenu.irrigationhistory;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.firebase.IrrigationRecordsListener;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseIrrigationRecord;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.home.HomeFragment;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.irrigationhistory.irrigationsrecyclerview.adapter.IrrigationRecordsListAdapter;
import com.ivanboyukliev.plantsirrigationsystem.utils.UserInputConverter;

import java.util.ArrayList;
import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DB_URL;

public class IrrigationHistoryViewModel extends ViewModel {
    private static List<FirebaseIrrigationRecord> irrigationRecords;
    private static IrrigationRecordsListAdapter irrigationRecordsAdapter;

    public IrrigationHistoryViewModel() {

        irrigationRecords = new ArrayList<>();
        irrigationRecordsAdapter = new IrrigationRecordsListAdapter(irrigationRecords);

        String standardBrokerUrl = PlantManagerActivity.getMqttClient().getBrokerUri();
        String firebaseBrokerUrl = UserInputConverter.convertServerURIToFirebaseRules(standardBrokerUrl);
        String currentPlantName = HomeFragment.getPlantName();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference currentPlantIrrigationRecords = FirebaseDatabase.getInstance(DB_URL)
                .getReference("users/" +
                        firebaseAuth.getUid()
                        + "/brokers/" +
                        firebaseBrokerUrl +
                        "/irrigations/" +
                        currentPlantName);

        currentPlantIrrigationRecords.addValueEventListener(new IrrigationRecordsListener());
    }

    public static List<FirebaseIrrigationRecord> getIrrigationRecords() {
        return irrigationRecords;
    }

    public static IrrigationRecordsListAdapter getIrrigationRecordsAdapter() {
        return irrigationRecordsAdapter;
    }
}
