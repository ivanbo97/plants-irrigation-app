package com.ivanboyukliev.plantsirrigationsystem.currentplantsrecyclerview.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.currentplantsrecyclerview.viewholder.BrokerPlantsViewHolder;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebasePlantObj;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.BROKER_DEL_ERROR;

public class BrokerPlantsRecyclerViewListAdapter extends RecyclerView.Adapter<BrokerPlantsViewHolder> {

    private List<FirebasePlantObj> brokerPlants;
    private String brokerID;
    private int brokerNumInList;

    public BrokerPlantsRecyclerViewListAdapter(List<FirebasePlantObj> brokerPlants, String brokerID, int brokerNumInList) {
        this.brokerPlants = brokerPlants;
        this.brokerID = brokerID;
        this.brokerNumInList = brokerNumInList;
    }

    @NonNull
    @Override
    public BrokerPlantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View brokerPlantView = LayoutInflater.from(parent.getContext()).inflate(R.layout.plants_list_row, parent, false);
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
            if (!HomeActivity.getMqttBrokersList().get(brokerNumInList).isConnected()) {
                HomeActivity.showBrokerMessage(BROKER_DEL_ERROR);
                return;
            }
            DatabaseReference currentBrokerPlant = HomeActivity.getmDatabaseAuthUserBrokers().child(brokerID + "/plants/" + plantName);
            currentBrokerPlant.removeValue((error, ref) -> {
                if (error == null) {
                    brokerPlants.remove(position);
                    notifyDataSetChanged();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return brokerPlants.size();
    }
}
