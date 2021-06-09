package com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.adapter;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBrokerClient;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.viewholder.MqttBrokerViewHolder;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.MqttAuthenticationDialog;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.MqttBrokerShowTopicsDialog;

import java.util.List;

public class BrokersRecyclerViewListAdapter extends RecyclerView.Adapter<MqttBrokerViewHolder> {

    private List<BasicMqttBrokerClient> mqttBrokers;

    public BrokersRecyclerViewListAdapter(List<BasicMqttBrokerClient> mqttBrokers) {
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
        BasicMqttBrokerClient brokerForBinding = mqttBrokers.get(position);
        holder.getBrokerNameTv().setText(brokerForBinding.getBrokerName());
        holder.getBrokerIpTv().setText(brokerForBinding.getBrokerIp() + ":" + brokerForBinding.getBrokerPort());
        holder.getDeleteBrokerButton().setOnClickListener(v -> {
            //For future we should delete record from DB also!!!!
            mqttBrokers.remove(position);
            notifyDataSetChanged();
        });

        holder.getConnectBtn().setOnClickListener(v -> {
            MqttAuthenticationDialog authenticationDialog = new MqttAuthenticationDialog(position);
            authenticationDialog.show(HomeActivity.getHomeActivityFragmentManager(), "MQTT Broker Authentication");
        });

        holder.getDisconnectBtn().setOnClickListener(v -> {
            mqttBrokers.get(position).disconnectClient();
        });

        holder.getShowTopicsTv().setOnClickListener(v -> {
            //Show dialog with card view
            MqttBrokerShowTopicsDialog showTopicsDialog = new MqttBrokerShowTopicsDialog(position);
            showTopicsDialog.show(HomeActivity.getHomeActivityFragmentManager(), "MQTT Broker Subscription Topics");
        });

        if (!brokerForBinding.isConnected()) {
            holder.getConnectBtn().setEnabled(true);
            holder.getDisconnectBtn().setEnabled(false);
        } else {
            holder.getConnectBtn().setEnabled(false);
            holder.getDisconnectBtn().setEnabled(true);
        }

    }

    @Override
    public int getItemCount() {
        return mqttBrokers.size();
    }

    public List<BasicMqttBrokerClient> getMqttBrokers() {
        return mqttBrokers;
    }
}
