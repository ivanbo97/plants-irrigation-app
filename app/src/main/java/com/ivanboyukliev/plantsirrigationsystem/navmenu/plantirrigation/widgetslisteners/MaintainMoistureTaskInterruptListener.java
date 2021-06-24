package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.PlantIrrigationFragment;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.MoistureManagementWidgets;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.ACTIVATE_MAINTAIN_MOISTURE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.ERROR_PUBLISH_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MOISTURE_MAINTAIN_INTERRUPT;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.SUCCESSFUL_MESSAGE_PUBLISH;

public class MaintainMoistureTaskInterruptListener implements View.OnClickListener {

    private Fragment parentFragment;
    private MoistureManagementWidgets moistureTaskWidgets;

    public MaintainMoistureTaskInterruptListener(PlantIrrigationFragment parentFragment, MoistureManagementWidgets moistureTaskWidgets) {
        this.parentFragment = parentFragment;
        this.moistureTaskWidgets = moistureTaskWidgets;
    }

    @Override
    public void onClick(View v) {
        MqttMessage interruptMoisterTaskMsg = new MqttMessage();
        interruptMoisterTaskMsg.setPayload(MOISTURE_MAINTAIN_INTERRUPT.getBytes());
        interruptMoisterTaskMsg.setQos(2);

        try {
            PlantManagerActivity.getMqttClient().getMqttAndroidClient().publish(ACTIVATE_MAINTAIN_MOISTURE_TOPIC, interruptMoisterTaskMsg);
            PlantManagerActivity.getIrrigationSystemState().setMoistureMaintainTaskRunning(false);
            Toast.makeText(parentFragment.getContext(), SUCCESSFUL_MESSAGE_PUBLISH + ACTIVATE_MAINTAIN_MOISTURE_TOPIC, Toast.LENGTH_LONG)
                    .show();
            v.setEnabled(false);
            moistureTaskWidgets.getSubmitMoistureBtn().setEnabled(true);
        } catch (MqttException e) {
            Toast.makeText(parentFragment.getContext(), ERROR_PUBLISH_MESSAGE + ACTIVATE_MAINTAIN_MOISTURE_TOPIC, Toast.LENGTH_LONG).show();
            PlantManagerActivity.getIrrigationSystemState().setMoistureMaintainTaskRunning(true);
            e.printStackTrace();
        }
    }
}
