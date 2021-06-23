package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.DelayedPumpStartWidgets;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_INTERRUPT;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_OPERATION_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.ERROR_PUBLISH_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.SUCCESSFUL_MESSAGE_PUBLISH;

public class TerminateDelayedStartBtnListener implements View.OnClickListener {

    private FragmentActivity parentFragment;

    public TerminateDelayedStartBtnListener(FragmentActivity parentFragment) {
        this.parentFragment = parentFragment;
    }

    @Override
    public void onClick(View v) {

        MqttMessage interruptDelayedStart = new MqttMessage();
        interruptDelayedStart.setPayload(DELAYED_START_INTERRUPT.getBytes());
        interruptDelayedStart.setQos(2);

        try {
            PlantManagerActivity.getMqttClient().getMqttAndroidClient().publish(DELAYED_START_OPERATION_TOPIC, interruptDelayedStart);
            Toast.makeText(parentFragment, SUCCESSFUL_MESSAGE_PUBLISH + DELAYED_START_OPERATION_TOPIC, Toast.LENGTH_LONG)
                    .show();
            v.setEnabled(false);
            DelayedPumpStartWidgets.getSubmitDelayedStartBtn().setEnabled(true);
        } catch (MqttException e) {

            Toast.makeText(parentFragment, ERROR_PUBLISH_MESSAGE + DELAYED_START_OPERATION_TOPIC, Toast.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }
}
