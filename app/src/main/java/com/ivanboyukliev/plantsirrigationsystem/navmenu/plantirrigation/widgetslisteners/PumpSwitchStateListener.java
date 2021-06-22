package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.IrrigationSystemState;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.ERROR_PUBLISH_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MANAGE_PUMP_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PUMP_ACTIVE_FLAG;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PUMP_IS_ACTIVE_MSG;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.SUCCESSFUL_MESSAGE_PUBLISH;

public class PumpSwitchStateListener implements CompoundButton.OnCheckedChangeListener {

    private IrrigationSystemState currentSystemState;
    private Context fragmentContext;

    public PumpSwitchStateListener(IrrigationSystemState irrigationSystemState, Context context) {
       currentSystemState = irrigationSystemState;
        fragmentContext = context;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (currentSystemState.isDelayedStartTaskOn() || currentSystemState.isMoistureMaintainTaskOn()) {
            Toast.makeText(fragmentContext, PUMP_IS_ACTIVE_MSG, Toast.LENGTH_LONG).show();
            //Returning switch position in its initial state
            buttonView.setChecked(!isChecked);
            return;
        }

        if (isChecked) {
            //Send message to broker to turn the pump on
            MqttMessage turnPumpOnMsg = new MqttMessage();
            turnPumpOnMsg.setPayload(PUMP_ACTIVE_FLAG.getBytes());
            turnPumpOnMsg.setQos(2);
            try {
                PlantManagerActivity.getMqttClient().getMqttAndroidClient().publish(MANAGE_PUMP_TOPIC, turnPumpOnMsg);
                Toast.makeText(fragmentContext, SUCCESSFUL_MESSAGE_PUBLISH + MANAGE_PUMP_TOPIC, Toast.LENGTH_LONG).show();
            } catch (MqttException e) {
                Toast.makeText(fragmentContext, ERROR_PUBLISH_MESSAGE + MANAGE_PUMP_TOPIC, Toast.LENGTH_LONG);
                e.printStackTrace();
            }
        }
    }
}
