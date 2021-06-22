package com.ivanboyukliev.plantsirrigationsystem.mqtt;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.api.MqttClientActions;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.home.HomeFragment;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_STATE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MAINTAIN_MOISTURE_TASK_STATE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MOISTURE_LEVEL_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PUMP_STATE_TOPIC;

public class AndroidMqttClientCallback implements MqttCallbackExtended {

    private MqttClientActions disconnectAction;

    private MutableLiveData<String> receivedMoisture;
    private MutableLiveData<String> receivedPumpState;
    private MutableLiveData<String> delayedIrrigationState;
    private MutableLiveData<String> moistureMaintainingOperationState;

    public AndroidMqttClientCallback(MqttClientActions disconnectAction) {
        this.disconnectAction = disconnectAction;
        receivedMoisture = new MutableLiveData<>();
        receivedPumpState = new MutableLiveData<>();
        delayedIrrigationState = new MutableLiveData<>();
        moistureMaintainingOperationState = new MutableLiveData<>();
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        Log.i("MQTT CLIENT STATUS:  ", "CONNECTED");
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.i("MQTT CLIENT STATUS:  ", "DISCONNECTED");
        HomeActivity.getBrokersAdapter().notifyDataSetChanged();
        HomeFragment.getConnectAndSubsBtn().setEnabled(true);
        HomeFragment.getBrokerDisconnectBtn().setEnabled(false);
        PlantManagerActivity.getMqttClient()
                .getBrokerConnState()
                .setValue(false);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.i("DATA FROM BROKER", message.toString());

        if (topic.equals(PUMP_STATE_TOPIC)) {
            Log.i("PUMP STATE", message.toString());
            receivedPumpState.setValue(message.toString());
            return;
        }

        if (topic.equals(MOISTURE_LEVEL_TOPIC)) {
            receivedMoisture.setValue(message.toString());
            return;
        }

        if (topic.equals(DELAYED_START_STATE_TOPIC)) {
            delayedIrrigationState.setValue(message.toString());
            return;
        }

        if (topic.equals(MAINTAIN_MOISTURE_TASK_STATE_TOPIC)) {
            moistureMaintainingOperationState.setValue(message.toString());
            return;
        }
    }

    public MutableLiveData<String> getReceivedMoisture() {
        return receivedMoisture;
    }

    public MutableLiveData<String> getReceivedPumpState() {
        return receivedPumpState;
    }

    public MutableLiveData<String> getDelayedIrrigationState() {
        return delayedIrrigationState;
    }

    public MutableLiveData<String> getMoistureMaintainingOperationState() {
        return moistureMaintainingOperationState;
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }


}
