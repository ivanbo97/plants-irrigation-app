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

import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.api.MqttRegDialogListener;
import com.ivanboyukliev.plantsirrigationsystem.utils.BasicMqttbroker;

public class MqttBrokerRegDialog extends AppCompatDialogFragment {

    private View dialogView;
    private EditText brokerNameWidget;
    private EditText brokerIpWidget;
    private EditText brokerPortWidget;
    private EditText brokerPasswordWidget; //for future security purpose
    private MqttRegDialogListener dialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.mqtt_broker_register, null);

        dialogBuilder.setView(dialogView)
                .setTitle("MQTT Broker Registration")
                .setNegativeButton("Cancel", (dialog, which) -> {
                    //no-action need for now
                })
                .setPositiveButton("Register", (dialog, which) -> {
                    BasicMqttbroker mqttbroker = new BasicMqttbroker(
                            brokerNameWidget.getText().toString(),
                            brokerIpWidget.getText().toString(),
                            brokerPortWidget.getText().toString());

                    dialogListener.createBrokerAsWidget(mqttbroker);

                });

        populateDialogWidgets();
        return dialogBuilder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (MqttRegDialogListener) context;
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
