package com.ivanboyukliev.plantsirrigationsystem.navmenu.irrigationhistory.irrigationsrecyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseIrrigationRecord;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.irrigationhistory.irrigationsrecyclerview.viewholder.IrrigationRecordViewHolder;

import java.util.List;

public class IrrigationRecordsListAdapter extends RecyclerView.Adapter<IrrigationRecordViewHolder> {

    private List<FirebaseIrrigationRecord> irrigationRecords;

    public IrrigationRecordsListAdapter(List<FirebaseIrrigationRecord> irrigationRecords) {
        this.irrigationRecords = irrigationRecords;
    }

    @NonNull
    @Override
    public IrrigationRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View irrigationHistoryRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.irrighistory_list_row, parent, false);
        IrrigationRecordViewHolder viewHolder = new IrrigationRecordViewHolder(irrigationHistoryRow);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IrrigationRecordViewHolder holder, int position) {
        FirebaseIrrigationRecord irrigationRecord = irrigationRecords.get(0);

        String irrigationDate = irrigationRecord.getDate();
        String irrigationStartTime = irrigationRecord.getStartTime();
        String irrigationEndTime = irrigationRecord.getEndTime();
        String moistureLvl = irrigationRecord.getMoistureLevel();

        holder.getIrrigationDateTv().setText(irrigationDate);
        holder.getStartTimeTv().setText(irrigationStartTime);
        holder.getEndTimeTv().setText(irrigationEndTime);
        holder.getMoistureLvlTv().setText(moistureLvl);
    }

    @Override
    public int getItemCount() {
        return irrigationRecords.size();
    }
}
