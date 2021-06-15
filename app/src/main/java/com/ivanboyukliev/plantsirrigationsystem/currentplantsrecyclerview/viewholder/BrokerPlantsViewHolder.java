package com.ivanboyukliev.plantsirrigationsystem.currentplantsrecyclerview.viewholder;


import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ivanboyukliev.plantsirrigationsystem.R;

public class BrokerPlantsViewHolder extends RecyclerView.ViewHolder {

    private TextView plantNameTv;
    private ImageView plantImage;
    private ImageButton deleteButton;

    public BrokerPlantsViewHolder(@NonNull View itemView) {
        super(itemView);
        plantNameTv = itemView.findViewById(R.id.plantNameCardView);
        plantImage = itemView.findViewById(R.id.plantImageView);
        deleteButton = itemView.findViewById(R.id.deletePlantBtn);
    }

    public TextView getPlantNameTv() {
        return plantNameTv;
    }

    public void setPlantNameTv(TextView plantNameTv) {
        this.plantNameTv = plantNameTv;
    }

    public ImageView getPlantImage() {
        return plantImage;
    }

    public void setPlantImage(ImageView plantImage) {
        this.plantImage = plantImage;
    }

    public ImageButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(FloatingActionButton deleteButton) {
        this.deleteButton = deleteButton;
    }
}
