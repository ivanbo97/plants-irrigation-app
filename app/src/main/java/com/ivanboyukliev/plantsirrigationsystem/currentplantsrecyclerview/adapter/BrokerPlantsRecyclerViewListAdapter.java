package com.ivanboyukliev.plantsirrigationsystem.currentplantsrecyclerview.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.currentplantsrecyclerview.viewholder.BrokerPlantsViewHolder;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.api.DeleteConfirmationListener;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.util.BasicDialogGenerator;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseBrokerObj;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebasePlantObj;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class BrokerPlantsRecyclerViewListAdapter extends RecyclerView.Adapter<BrokerPlantsViewHolder> implements DeleteConfirmationListener {

    private List<FirebasePlantObj> brokerPlants;
    private String brokerID;
    private int brokerNumInList;
    private int plantForDeleteionIdx;
    private Context plantsListContext;
    private DatabaseReference currentPlantIrrigationRecords;

    public BrokerPlantsRecyclerViewListAdapter(List<FirebasePlantObj> brokerPlants, String brokerID, int brokerNumInList) {
        this.brokerPlants = brokerPlants;
        this.brokerID = brokerID;
        this.brokerNumInList = brokerNumInList;
    }

    @NonNull
    @Override
    public BrokerPlantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        plantsListContext = parent.getContext();
        View brokerPlantView = LayoutInflater.from(plantsListContext).inflate(R.layout.plants_list_row, parent, false);
        BrokerPlantsViewHolder plantsViewHolder = new BrokerPlantsViewHolder(brokerPlantView);
        return plantsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BrokerPlantsViewHolder holder, int position) {
        FirebasePlantObj currentPlant = brokerPlants.get(position);
        String plantName = currentPlant.getPlantName();
        holder.getPlantNameTv().setText(plantName);

        Picasso.get().load(currentPlant.getImageURL())
                .resize(120, 100)
                .into(holder.getPlantImage());

        holder.getDeleteButton().setOnClickListener(v -> {
            plantForDeleteionIdx = position;
            BasicDialogGenerator dialogGenerator = new BasicDialogGenerator(this);
            currentPlantIrrigationRecords = HomeActivity.getmDatabaseAuthUserBrokers().child(brokerID + "/irrigations/" + plantName);
            DatabaseReference currentBrokerPlant = HomeActivity.getmDatabaseAuthUserBrokers().child(brokerID + "/plants/" + plantName);
            AlertDialog plantDeletionDialog = dialogGenerator.generateDeleteConfirmation(currentBrokerPlant, plantsListContext);
            plantDeletionDialog.show();
        });

        holder.getPlantNameTv().setOnClickListener(v -> {
            Intent intent = new Intent(plantsListContext, PlantManagerActivity.class);
            FirebaseBrokerObj currentBroker = HomeActivity.getMqttBrokersList().get(brokerNumInList);
            String brokerName = currentBroker.getBrokerName();
            String brokerUrl = currentBroker.getBrokerIp() + ":" + currentBroker.getBrokerPort();
            intent.putExtra("PlantName", plantName);
            intent.putExtra("BrokerName", brokerName);
            intent.putExtra("BrokerUrl", brokerUrl);
            intent.putExtra("TopicsList", (Serializable) currentBroker.getTopics());
            plantsListContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return brokerPlants.size();
    }

    @Override
    public void onDeleteConfirmationSent() {
        brokerPlants.remove(plantForDeleteionIdx);
        currentPlantIrrigationRecords.removeValue();
        notifyDataSetChanged();
    }
}
