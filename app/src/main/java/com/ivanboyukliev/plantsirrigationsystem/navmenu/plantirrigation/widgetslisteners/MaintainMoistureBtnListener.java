package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.PlantIrrigationFragment;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.IrrigationSystemState;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.ACTIVATE_MAINTAIN_MOISTURE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.EMPTY_MOISTURE_FIELD;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.ERROR_PUBLISH_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MAINTAIN_MOISTURE_VALUE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MOISTURE_MAINTAIN_FLAG;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PUMP_BUSY_MSG;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.SUCCESSFUL_MESSAGE_PUBLISH;

public class MaintainMoistureBtnListener implements View.OnClickListener {

    private IrrigationSystemState currentSystemState;
    private Context activityContext;

    public MaintainMoistureBtnListener(IrrigationSystemState irrigationSystemState, Context context) {
        this.currentSystemState = irrigationSystemState;
        this.activityContext = context;
    }

    @Override
    public void onClick(View v) {

        String desiredMoisture = PlantIrrigationFragment.getEnteredMoisture().getText().toString();
        Log.i("DESIRED MOISTURE", desiredMoisture);
        if (desiredMoisture.equals("")) {
            Toast.makeText(activityContext, EMPTY_MOISTURE_FIELD, Toast.LENGTH_SHORT).show();
            return;
        }
        if (currentSystemState.isPumpActive() || currentSystemState.isMoistureMaintainTaskOn() || currentSystemState.isDelayedStartTaskOn()) {
            Toast.makeText(activityContext, PUMP_BUSY_MSG, Toast.LENGTH_SHORT).show();
            return;
        }


        MqttMessage initMoistureTask = new MqttMessage();
        initMoistureTask.setPayload(MOISTURE_MAINTAIN_FLAG.getBytes());
        initMoistureTask.setQos(2);

        MqttMessage moistureValue = new MqttMessage();
        moistureValue.setPayload(desiredMoisture.getBytes());
        moistureValue.setQos(2);

        try {
            PlantManagerActivity.getMqttClient().getMqttAndroidClient().publish(ACTIVATE_MAINTAIN_MOISTURE_TOPIC, initMoistureTask);
            PlantManagerActivity.getMqttClient().getMqttAndroidClient().publish(MAINTAIN_MOISTURE_VALUE_TOPIC, moistureValue);
            Toast.makeText(activityContext, SUCCESSFUL_MESSAGE_PUBLISH + ACTIVATE_MAINTAIN_MOISTURE_TOPIC +
                    "," + MAINTAIN_MOISTURE_VALUE_TOPIC, Toast.LENGTH_LONG).show();
        } catch (MqttException e) {
            Toast.makeText(activityContext, ERROR_PUBLISH_MESSAGE + ACTIVATE_MAINTAIN_MOISTURE_TOPIC + "/"
                    + MAINTAIN_MOISTURE_VALUE_TOPIC, Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
    }
}
