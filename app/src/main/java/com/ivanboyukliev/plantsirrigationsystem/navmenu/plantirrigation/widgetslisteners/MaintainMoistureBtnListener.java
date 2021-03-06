package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.AndroidMqttClientCallback;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.PlantIrrigationFragment;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.MoistureManagementWidgets;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.ACTIVATE_MAINTAIN_MOISTURE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.EMPTY_MOISTURE_FIELD;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.ERROR_PUBLISH_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MAINTAIN_MOISTURE_VALUE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MOISTURE_MAINTAIN_FLAG;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.SUCCESSFUL_MESSAGE_PUBLISH;

public class MaintainMoistureBtnListener implements View.OnClickListener {

    private Fragment parentFragment;
    private MoistureManagementWidgets moistureTaskWidgets;

    public MaintainMoistureBtnListener(PlantIrrigationFragment parentFragment, MoistureManagementWidgets moistureTaskWidgets) {
        this.parentFragment = parentFragment;
        this.moistureTaskWidgets = moistureTaskWidgets;
    }

    @Override
    public void onClick(View v) {

        String desiredMoisture = moistureTaskWidgets.getEnteredMoisture().getText().toString();
        Log.i("DESIRED MOISTURE", desiredMoisture);
        if (desiredMoisture.equals("")) {
            Toast.makeText(parentFragment.getContext(), EMPTY_MOISTURE_FIELD, Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        MqttMessage initMoistureTask = new MqttMessage();
        initMoistureTask.setPayload(MOISTURE_MAINTAIN_FLAG.getBytes());
        initMoistureTask.setQos(2);

        MqttMessage moistureValue = new MqttMessage();
        moistureValue.setPayload(desiredMoisture.getBytes());
        moistureValue.setQos(2);

        try {
            MqttAndroidClient mqttAndroidClient = PlantManagerActivity.getMqttClient().getMqttAndroidClient();
            mqttAndroidClient.publish(ACTIVATE_MAINTAIN_MOISTURE_TOPIC, initMoistureTask);
            mqttAndroidClient.publish(MAINTAIN_MOISTURE_VALUE_TOPIC, moistureValue);

            Toast.makeText(parentFragment.getContext(), SUCCESSFUL_MESSAGE_PUBLISH + ACTIVATE_MAINTAIN_MOISTURE_TOPIC +
                    "," + MAINTAIN_MOISTURE_VALUE_TOPIC, Toast.LENGTH_LONG).show();
        } catch (MqttException e) {
            Toast.makeText(parentFragment.getContext(), ERROR_PUBLISH_MESSAGE + ACTIVATE_MAINTAIN_MOISTURE_TOPIC + "/"
                    + MAINTAIN_MOISTURE_VALUE_TOPIC, Toast.LENGTH_LONG).show();

            ((AndroidMqttClientCallback) PlantManagerActivity.getMqttClient().getMqttCallback())
                    .getIrrigationSystemState()
                    .setMoistureMaintainTaskRunning(false);
            e.printStackTrace();
        }
    }
}
