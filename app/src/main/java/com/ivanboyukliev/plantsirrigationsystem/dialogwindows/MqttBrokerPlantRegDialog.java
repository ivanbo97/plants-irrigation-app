package com.ivanboyukliev.plantsirrigationsystem.dialogwindows;

import android.app.AlertDialog;
import android.app.Dialog;

import android.os.Bundle;

import android.util.Log;
import android.util.SparseBooleanArray;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.SearchView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebasePlantObj;
import com.ivanboyukliev.plantsirrigationsystem.firebase.util.FirebaseDataImporter;
import com.ivanboyukliev.plantsirrigationsystem.plantapi.PlantSearchListener;
import com.ivanboyukliev.plantsirrigationsystem.searchedplantsrecyclerview.adapter.PlantsFromApiListAdapter;


import java.util.ArrayList;
import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.ADD_PLANT_DIALOG_POS_BTN;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.ADD_PLANT_DIALOG_TILE;

public class MqttBrokerPlantRegDialog extends AppCompatDialogFragment {

    private View dialogView;
    private SearchView searchView;
    private int brokerNumInList;
    private String brokerId;

    private List<FirebasePlantObj> retrievedPlants;
    private RecyclerView retrievedPlantsRecycleView;
    private static PlantsFromApiListAdapter retrievedPlantsAdapter;

    private FragmentActivity dialogFragment;


    public MqttBrokerPlantRegDialog(int brokerNumInList, String brokerId) {
        this.brokerNumInList = brokerNumInList;
        this.brokerId = brokerId;
        retrievedPlants = new ArrayList<>();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialogFragment = getActivity();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(dialogFragment);
        LayoutInflater inflater = dialogFragment.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.mqtt_broker_plant_reg, null);
        populateWidgets();
        retrievedPlantsAdapter = new PlantsFromApiListAdapter(retrievedPlants);

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(dialogFragment, LinearLayoutManager.VERTICAL, false);

        retrievedPlantsRecycleView.setLayoutManager(verticalLayoutManager);

        retrievedPlantsRecycleView.setAdapter(retrievedPlantsAdapter);

        searchView.setQueryHint("Type key word here...");

        SearchView.OnQueryTextListener plantSearchListener = new PlantSearchListener(retrievedPlants, dialogFragment);
        searchView.setOnQueryTextListener(plantSearchListener);

        dialogBuilder.setView(dialogView)
                .setTitle(ADD_PLANT_DIALOG_TILE)
                .setPositiveButton(ADD_PLANT_DIALOG_POS_BTN, (dialog, which) -> {
                    //Check which plant is check and add to list and DB.
                    SparseBooleanArray checkedPlantsIndexes = PlantsFromApiListAdapter.getCurrentCheckBoxesState();

                    List<FirebasePlantObj> userSelectedPlants = new ArrayList<>();

                    retrieveCheckedPlants(checkedPlantsIndexes, userSelectedPlants);
                    DatabaseReference plantDB = HomeActivity.getmDatabaseAuthUserBrokers().child(brokerId).child("plants");
                    FirebaseDataImporter.importPlantsData(plantDB, userSelectedPlants);

                    populateBrokerPlantsList(userSelectedPlants);
                    retrievedPlantsAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    retrievedPlants.clear();
                });

        return dialogBuilder.create();
    }

    private void populateBrokerPlantsList(List<FirebasePlantObj> checkedPlants) {
        for (FirebasePlantObj checkedPlant : checkedPlants) {
            HomeActivity
                    .getMqttBrokersList()
                    .get(brokerNumInList)
                    .getPlants().
                    add(checkedPlant);
        }
    }

    private void retrieveCheckedPlants(SparseBooleanArray checkedPlantsIndexes, List<FirebasePlantObj> checkedPlants) {
        for (int i = 0; i < checkedPlantsIndexes.size(); i++) {
            int checkedPlantPos = checkedPlantsIndexes.keyAt(i);
            checkedPlants.add(retrievedPlants.get(checkedPlantPos));
        }
    }

    private void populateWidgets() {
        searchView = dialogView.findViewById(R.id.searchViewId);
        retrievedPlantsRecycleView = dialogView.findViewById(R.id.plantApiListRecyclerView);
    }

    public static PlantsFromApiListAdapter getRetrievedPlantsAdapter() {
        return retrievedPlantsAdapter;
    }
}
