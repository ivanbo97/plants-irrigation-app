package com.ivanboyukliev.plantsirrigationsystem.navmenu.irrigationhistory.irrigationsrecyclerview.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanboyukliev.plantsirrigationsystem.R;

public class IrrigationRecordViewHolder extends RecyclerView.ViewHolder {

    private TextView irrigationDateTv;
    private TextView startTimeTv;
    private TextView endTimeTv;
    private TextView moistureLvlTv;

    public IrrigationRecordViewHolder(@NonNull View itemView) {
        super(itemView);
        irrigationDateTv = itemView.findViewById(R.id.startIrrigDateTv);
        startTimeTv = itemView.findViewById(R.id.startIrrigTimeTv);
        endTimeTv = itemView.findViewById(R.id.endIrrigTimeTv);
        moistureLvlTv = itemView.findViewById(R.id.moistureLvLTv);
    }

    public TextView getIrrigationDateTv() {
        return irrigationDateTv;
    }

    public void setIrrigationDateTv(TextView irrigationDateTv) {
        this.irrigationDateTv = irrigationDateTv;
    }

    public TextView getStartTimeTv() {
        return startTimeTv;
    }

    public void setStartTimeTv(TextView startTimeTv) {
        this.startTimeTv = startTimeTv;
    }

    public TextView getEndTimeTv() {
        return endTimeTv;
    }

    public void setEndTimeTv(TextView endTimeTv) {
        this.endTimeTv = endTimeTv;
    }

    public TextView getMoistureLvlTv() {
        return moistureLvlTv;
    }

    public void setMoistureLvlTv(TextView moistureLvlTv) {
        this.moistureLvlTv = moistureLvlTv;
    }
}
