package com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.adapter;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBroker;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.viewholder.MqttBrokerViewHolder;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.api.MqttClientActions;

import java.util.List;

public class BrokersRecyclerViewListAdapter extends RecyclerView.Adapter<MqttBrokerViewHolder> {

    private List<BasicMqttBroker> mqttBrokers;
    private List<MqttClientActions> mqttClientActionsList;

    public BrokersRecyclerViewListAdapter(List<BasicMqttBroker> mqttBrokers, List<MqttClientActions> mqttClientActionsList) {
        this.mqttBrokers = mqttBrokers;
        this.mqttClientActionsList = mqttClientActionsList;
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
        BasicMqttBroker brokerForBinding = mqttBrokers.get(position);
        holder.getBrokerNameTv().setText(brokerForBinding.getBrokerName());
        holder.getBrokerIpTv().setText(brokerForBinding.getBrokerIp() + ":" + brokerForBinding.getBrokerPort());
        holder.getDeleteBrokerButton().setOnClickListener(v -> {
            //For future we should delete record from DB also!!!!
            mqttBrokers.remove(position);
            mqttClientActionsList.remove(position);
            notifyDataSetChanged();
        });

        holder.getConnectBtn().setOnClickListener(v -> {
            mqttClientActionsList.get(position).connectClient();
        });
    }

    @Override
    public int getItemCount() {
        return mqttBrokers.size();
    }

    public List<BasicMqttBroker> getMqttBrokers() {
        return mqttBrokers;
    }
}
