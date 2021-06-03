package com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ivanboyukliev.plantsirrigationsystem.R;

public class MqttBrokerViewHolder extends RecyclerView.ViewHolder {

    private TextView brokerNameTv;
    private TextView brokerIpTv;
    private Button connectBtn;
    private Button disconnectBtn;
    private FloatingActionButton deleteBrokerButton;


    public MqttBrokerViewHolder(@NonNull View itemView) {
        super(itemView);
        brokerNameTv = itemView.findViewById(R.id.brokerNameTv);
        brokerIpTv = itemView.findViewById(R.id.brokerIP);
        connectBtn = itemView.findViewById(R.id.connectBrokerBtn);
        disconnectBtn = itemView.findViewById(R.id.disconnectBrokerBtn);
        deleteBrokerButton = itemView.findViewById(R.id.deleteBrokerButton);

    }

    public TextView getBrokerNameTv() {
        return brokerNameTv;
    }

    public void setBrokerNameTv(TextView brokerNameTv) {
        this.brokerNameTv = brokerNameTv;
    }

    public TextView getBrokerIpTv() {
        return brokerIpTv;
    }

    public void setBrokerIpTv(TextView brokerIpTv) {
        this.brokerIpTv = brokerIpTv;
    }

    public Button getConnectBtn() {
        return connectBtn;
    }

    public void setConnectBtn(Button connectBtn) {
        this.connectBtn = connectBtn;
    }

    public Button getDisconnectBtn() {
        return disconnectBtn;
    }

    public void setDisconnectBtn(Button disconnectBtn) {
        this.disconnectBtn = disconnectBtn;
    }

    public FloatingActionButton getDeleteBrokerButton() {
        return deleteBrokerButton;
    }

    public void setDeleteBrokerButton(FloatingActionButton deleteBrokerButton) {
        this.deleteBrokerButton = deleteBrokerButton;
    }
}
