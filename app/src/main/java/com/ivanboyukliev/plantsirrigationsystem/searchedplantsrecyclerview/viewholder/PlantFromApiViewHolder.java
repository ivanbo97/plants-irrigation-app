package com.ivanboyukliev.plantsirrigationsystem.searchedplantsrecyclerview.viewholder;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.searchedplantsrecyclerview.adapter.PlantsFromApiListAdapter;

public class PlantFromApiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imageView;
    private TextView txtview;
    private CheckBox checkBox;


    public PlantFromApiViewHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.idPlantApiImage);
        txtview = view.findViewById(R.id.idPlantApiName);
        checkBox = view.findViewById(R.id.idCheckBox);
        checkBox.setOnClickListener(this);
    }

    public void restoreCheckBoxState(int position) {

        SparseBooleanArray itemStateArray = PlantsFromApiListAdapter.getCurrentCheckBoxesState();
        if (!itemStateArray.get(position, false)) {
            Log.i("Check false, position:", String.valueOf(position));
            checkBox.setChecked(false);
        } else {
            Log.i("Check true, position:", String.valueOf(position));
            checkBox.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {

        int adapterPosition = getAdapterPosition();
        Log.i("AdapterPosition", String.valueOf(adapterPosition));
        SparseBooleanArray itemStateArray = PlantsFromApiListAdapter.getCurrentCheckBoxesState();
        if (!itemStateArray.get(adapterPosition, false)) {
            checkBox.setChecked(true);
            itemStateArray.put(adapterPosition, true);
        } else {
            checkBox.setChecked(false);
            itemStateArray.put(adapterPosition, false);
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTxtview() {
        return txtview;
    }

    public void setTxtview(TextView txtview) {
        this.txtview = txtview;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
