package com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.adapter;

import android.annotation.SuppressLint;

import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.viewholder.MqttBrokerViewHolder;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.MqttBrokerShowPlantsDialog;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.MqttBrokerShowTopicsDialog;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseBrokerObj;
import com.ivanboyukliev.plantsirrigationsystem.firebase.util.FirebaseDataImporter;

import java.util.List;

public class BrokersRecyclerViewListAdapter extends RecyclerView.Adapter<MqttBrokerViewHolder> {

    private List<FirebaseBrokerObj> mqttBrokers;

    public BrokersRecyclerViewListAdapter(List<FirebaseBrokerObj> mqttBrokers) {
        this.mqttBrokers = mqttBrokers;
    }

    @NonNull
    @Override
    public MqttBrokerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View brokerRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.broker_list_row, parent, false);
        MqttBrokerViewHolder brokerViewHolder = new MqttBrokerViewHolder(brokerRowView);
        return brokerViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MqttBrokerViewHolder holder, int position) {
        FirebaseBrokerObj brokerForBinding = mqttBrokers.get(position);
        EditText brokerUrlTv = holder.getBrokerIpTv();
        Button saveChangesBtn = holder.getSaveChangesBtn();
        holder.getBrokerNameTv().setText(brokerForBinding.getBrokerName());
        brokerUrlTv.setText(brokerForBinding.getBrokerIp() + ":" + brokerForBinding.getBrokerPort());
        holder.getDeleteBrokerButton().setOnClickListener(v -> {
            DatabaseReference currentBroker = HomeActivity.getmDatabaseAuthUserBrokers().child(brokerForBinding.getBrokerID());
            currentBroker.removeValue();
            mqttBrokers.remove(position);
            notifyDataSetChanged();
        });

        holder.getEditBrokerDataBtn().setOnClickListener(v -> {
            brokerUrlTv.setKeyListener((KeyListener) brokerUrlTv.getTag());
            saveChangesBtn.setEnabled(true);
        });

        saveChangesBtn.setOnClickListener(v -> {
            FirebaseDataImporter.updateBrokerData(brokerForBinding, brokerUrlTv);
            notifyDataSetChanged();
            saveChangesBtn.setEnabled(false);
        });
        holder.getShowTopicsTv().setOnClickListener(v -> {
            MqttBrokerShowTopicsDialog showTopicsDialog = new MqttBrokerShowTopicsDialog(position);
            showTopicsDialog.show(HomeActivity.getHomeActivityFragmentManager(), "MQTT Broker Subscription Topics");
        });

        holder.getShowPlantsTv().setOnClickListener(v -> {
            MqttBrokerShowPlantsDialog showPlantsDialog = new MqttBrokerShowPlantsDialog(position);
            showPlantsDialog.show(HomeActivity.getHomeActivityFragmentManager(), "MQTT Broker Current Plants");
        });
    }

    @Override
    public int getItemCount() {
        return mqttBrokers.size();
    }

    public List<FirebaseBrokerObj> getMqttBrokers() {
        return mqttBrokers;
    }
}
