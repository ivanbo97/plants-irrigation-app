package com.ivanboyukliev.plantsirrigationsystem.searchedplantsrecyclerview.adapter;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebasePlantObj;
import com.ivanboyukliev.plantsirrigationsystem.searchedplantsrecyclerview.viewholder.PlantFromApiViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlantsFromApiListAdapter extends RecyclerView.Adapter<PlantFromApiViewHolder> {

    private static List<FirebasePlantObj> plantsFromApi;
    private static SparseBooleanArray currentCheckBoxesState = new SparseBooleanArray();

    public PlantsFromApiListAdapter(List<FirebasePlantObj> plantsList) {
        this.plantsFromApi = plantsList;
        currentCheckBoxesState.clear();
    }

    @NonNull
    @Override
    public PlantFromApiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View plantView = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_api_row, parent, false);
        PlantFromApiViewHolder plantViewHolder = new PlantFromApiViewHolder(plantView);
        return plantViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlantFromApiViewHolder holder, int position) {
        FirebasePlantObj plantForBinding = plantsFromApi.get(position);
        Picasso.get().load(plantForBinding.getImageURL())
                .resize(120, 60).into(holder.getImageView());
        holder.getTxtview().setText(plantForBinding.getPlantName());
        holder.getImageView().setOnClickListener(v -> {
            String productName = plantsFromApi.get(position).getPlantName().toString();
            //Fore future purpose - extend image.
        });

        holder.restoreCheckBoxState(position);
    }

    @Override
    public int getItemCount() {
        return plantsFromApi.size();
    }

    public static SparseBooleanArray getCurrentCheckBoxesState() {
        return currentCheckBoxesState;
    }

    public static List<FirebasePlantObj> getPlantsFromApi() {
        return plantsFromApi;
    }
}
