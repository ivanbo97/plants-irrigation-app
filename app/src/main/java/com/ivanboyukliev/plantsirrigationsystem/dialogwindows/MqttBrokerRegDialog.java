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

import com.google.firebase.database.DatabaseReference;
import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.api.BrokerDataInputListener;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBrokerClient;
import com.ivanboyukliev.plantsirrigationsystem.utils.UserInputConverter;
import com.ivanboyukliev.plantsirrigationsystem.utils.UserInputValidator;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.BROKER_REG_DIALOG_TITLE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.INCORRECT_PORT_MESSAGE;

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
                .setTitle(BROKER_REG_DIALOG_TITLE)
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("Register", null);
        
        return dialogBuilder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String brokerPort = brokerPortWidget.getText().toString();
            BasicMqttBrokerClient newBroker = new BasicMqttBrokerClient();
            String brokerName = brokerNameWidget.getText().toString();
            newBroker.setBrokerName(brokerName);
            String brokerIp = brokerIpWidget.getText().toString();
            newBroker.setBrokerIp(brokerIp);
            if (!UserInputValidator.isPortValid(brokerPort)) {
                brokerPortWidget.setError(INCORRECT_PORT_MESSAGE);
                return;
            }
            newBroker.setBrokerPort(brokerPort);
            newBroker.initClientData();
            HomeActivity.getMqttBrokersList().add(newBroker);
            dialogListener.onBrokerDataSending();
            DatabaseReference databaseUserBrokers = HomeActivity.getmDatabaseAuthUserBrokers();
            String newStandartBrokerId = brokerIp + ":" + brokerPort;
            String firebaseBrokerId = UserInputConverter.convertServerURIToFirebaseRules(newStandartBrokerId);
            databaseUserBrokers.child(firebaseBrokerId).child("brkName").setValue(brokerName);
            newBroker.setBrokerID(firebaseBrokerId);
            dialog.dismiss();
        });
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