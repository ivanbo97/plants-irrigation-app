package com.ivanboyukliev.plantsirrigationsystem.dialogwindows;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.api.BrokerDataInputListener;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBroker;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.api.MqttClientActions;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.impl.MqttClientActionsImpl;

public class MqttBrokerRegDialog extends AppCompatDialogFragment {

    private View dialogView;
    private EditText brokerNameWidget;
    private EditText brokerIpWidget;
    private EditText brokerPortWidget;
    private BrokerDataInputListener dialogListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.mqtt_broker_register, null);
        populateDialogWidgets();
        dialogBuilder.setView(dialogView)
                .setTitle("MQTT Broker Registration")
                .setNegativeButton("Cancel", (dialog, which) -> {
                    //no-action needed for now
                })
                .setPositiveButton("Register", (dialog, which) -> {

                    BasicMqttBroker newBroker = new BasicMqttBroker();
                    newBroker.setBrokerName(brokerNameWidget.getText().toString());
                    newBroker.setBrokerIp(brokerIpWidget.getText().toString());
                    newBroker.setBrokerPort(brokerPortWidget.getText().toString());
                    HomeActivity.getMqttBrokersList().add(newBroker);
                    MqttClientActions mqttClientActions = new MqttClientActionsImpl(newBroker);
                    HomeActivity.getMqttClientActionsList().add(mqttClientActions);
                    dialogListener.onBrokerDataSending();
                });
        return dialogBuilder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (BrokerDataInputListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement MqttRegDialogListener");
        }
    }

    private void populateDialogWidgets() {
        brokerNameWidget = dialogView.findViewById(R.id.mqtt_broker_name);
        brokerIpWidget = dialogView.findViewById(R.id.mqtt_broker_ip);
        brokerPortWidget = dialogView.findViewById(R.id.mqtt_broker_port);
    }
}