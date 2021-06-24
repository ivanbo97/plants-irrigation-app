package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.DelayedPumpStartWidgets;
import com.ivanboyukliev.plantsirrigationsystem.utils.UserInputValidator;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_DATE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_DURATION_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_INIT_FLAG;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_OPERATION_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_TIME_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_TOPICS;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.ERROR_PUBLISH_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.INCOMPLETE_DATA_DELAYED_START;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.SUCCESSFUL_MESSAGE_PUBLISH;

public class SubmitDelayedStartBtnListener implements View.OnClickListener {

    private FragmentActivity parentFragment;
    private DelayedPumpStartWidgets delayedStartWidgets;

    public SubmitDelayedStartBtnListener(FragmentActivity parentFragment, DelayedPumpStartWidgets delayedStartWidgets) {
        this.parentFragment = parentFragment;
        this.delayedStartWidgets = delayedStartWidgets;
    }

    @Override
    public void onClick(View v) {

        String enteredDate = delayedStartWidgets.getInputDateTv().getText().toString();
        String enteredTime = delayedStartWidgets.getInputTimeTv().getText().toString();
        String enteredDuration = delayedStartWidgets.getEnteredIrrigationDuration().getText().toString();

        if (!UserInputValidator.isDelayedStartDataEntered(enteredDate, enteredTime, enteredDuration)) {
            Toast.makeText(parentFragment, INCOMPLETE_DATA_DELAYED_START, Toast.LENGTH_LONG)
                    .show();
            return;
        }

        MqttMessage initDelayStartTask = new MqttMessage();
        initDelayStartTask.setPayload(DELAYED_START_INIT_FLAG.getBytes());
        initDelayStartTask.setQos(2);

        MqttMessage dateOfDelayedStart = new MqttMessage();
        dateOfDelayedStart.setPayload(enteredDate.getBytes());
        dateOfDelayedStart.setQos(2);

        MqttMessage timeOfDelayedStart = new MqttMessage();
        timeOfDelayedStart.setPayload(enteredTime.getBytes());
        timeOfDelayedStart.setQos(2);

        MqttMessage durationOfIrrigation = new MqttMessage();
        durationOfIrrigation.setPayload(enteredDuration.getBytes());
        durationOfIrrigation.setQos(2);

        MqttAndroidClient currentMqttClient = PlantManagerActivity.getMqttClient().getMqttAndroidClient();

        try {
            currentMqttClient.publish(DELAYED_START_OPERATION_TOPIC, initDelayStartTask);
            currentMqttClient.publish(DELAYED_START_DATE_TOPIC, dateOfDelayedStart);
            currentMqttClient.publish(DELAYED_START_TIME_TOPIC, timeOfDelayedStart);
            currentMqttClient.publish(DELAYED_START_DURATION_TOPIC, durationOfIrrigation);
            Toast.makeText(parentFragment, SUCCESSFUL_MESSAGE_PUBLISH + DELAYED_START_TOPICS, Toast.LENGTH_LONG)
                    .show();

        } catch (MqttException e) {
            Toast.makeText(parentFragment, ERROR_PUBLISH_MESSAGE + DELAYED_START_TOPICS, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
