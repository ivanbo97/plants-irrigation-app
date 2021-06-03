package com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBroker;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.viewholder.MqttBrokerViewHolder;

public class BrokersRecyclerViewListAdapter extends RecyclerView.Adapter<MqttBrokerViewHolder> {

    private BasicMqttBroker mqttBroker;

    public BrokersRecyclerViewListAdapter(BasicMqttBroker mqttBroker) {
        this.mqttBroker = mqttBroker;
    }

    @NonNull
    @Override
    public MqttBrokerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  brokerRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.broker_list_row,parent,false);
        MqttBrokerViewHolder brokerViewHolder =  new MqttBrokerViewHolder(brokerRowView);
        return  brokerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MqttBrokerViewHolder holder, int position) {
        holder.getBrokerNameTv().setText(mqttBroker.getBrokerName());
        holder.getBrokerIpTv().setText(mqttBroker.getBrokerIp());
    }

    @Override
    public int getItemCount() {
        /*!!!Should be refactored when DB functionality for storing user brokers is ready!!!!*/
        if(mqttBroker == null)
            return 0;
        else
            return 1;
    }

    public BasicMqttBroker getMqttBroker() {
        return mqttBroker;
    }
}
