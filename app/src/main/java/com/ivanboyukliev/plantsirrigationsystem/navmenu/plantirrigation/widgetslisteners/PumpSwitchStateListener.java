package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners;

import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.ERROR_PUBLISH_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MANAGE_PUMP_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PUMP_ACTIVE_FLAG;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PUMP_INACTIVE_FLAG;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.SUCCESSFUL_MESSAGE_PUBLISH;

public class PumpSwitchStateListener implements CompoundButton.OnCheckedChangeListener {

    private Fragment fragmentContext;

    public PumpSwitchStateListener(Fragment fragment) {
        fragmentContext = fragment;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {
            //Send message to broker to turn the pump on
            MqttMessage turnPumpOnMsg = new MqttMessage();
            turnPumpOnMsg.setPayload(PUMP_ACTIVE_FLAG.getBytes());
            turnPumpOnMsg.setQos(2);
            try {
                PlantManagerActivity.getMqttClient().getMqttAndroidClient().publish(MANAGE_PUMP_TOPIC, turnPumpOnMsg);
                Toast.makeText(fragmentContext.getContext(), SUCCESSFUL_MESSAGE_PUBLISH + MANAGE_PUMP_TOPIC, Toast.LENGTH_LONG).show();
            } catch (MqttException e) {
                Toast.makeText(fragmentContext.getContext(), ERROR_PUBLISH_MESSAGE + MANAGE_PUMP_TOPIC, Toast.LENGTH_LONG);
                e.printStackTrace();
            }
            return;
        }

        MqttMessage turnPumpOffMsg = new MqttMessage();
        turnPumpOffMsg.setPayload(PUMP_INACTIVE_FLAG.getBytes());
        turnPumpOffMsg.setQos(2);

        try {
            PlantManagerActivity.getMqttClient().getMqttAndroidClient().publish(MANAGE_PUMP_TOPIC, turnPumpOffMsg);
            Toast.makeText(fragmentContext.getContext(), SUCCESSFUL_MESSAGE_PUBLISH + MANAGE_PUMP_TOPIC, Toast.LENGTH_LONG).show();
        } catch (MqttException e) {
            Toast.makeText(fragmentContext.getContext(), ERROR_PUBLISH_MESSAGE + MANAGE_PUMP_TOPIC, Toast.LENGTH_LONG);
            e.printStackTrace();
        }
    }
}
