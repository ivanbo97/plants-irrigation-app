package com.ivanboyukliev.plantsirrigationsystem.mqtt;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.api.MqttClientActions;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.home.HomeFragment;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.IrrigationSystemMutableLiveData;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.IrrigationSystemState;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_STATE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MAINTAIN_MOISTURE_TASK_STATE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MOISTURE_LEVEL_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PUMP_STATE_TOPIC;

public class AndroidMqttClientCallback implements MqttCallbackExtended {

    private MqttClientActions disconnectAction;
    private IrrigationSystemMutableLiveData<IrrigationSystemState> currentIrrigationSystemState;
    private MutableLiveData<String> moistureValue;
    private IrrigationSystemState irrigationSystemState;

    public AndroidMqttClientCallback(MqttClientActions disconnectAction) {
        this.disconnectAction = disconnectAction;
        irrigationSystemState = new IrrigationSystemState();
        currentIrrigationSystemState = new IrrigationSystemMutableLiveData<>();
        currentIrrigationSystemState.setValue(irrigationSystemState);
        moistureValue = new MutableLiveData<>();
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

        String receivedMessage = message.toString();
        boolean operationState;
        if (receivedMessage.equals("on")) {
            operationState = true;
        } else if (receivedMessage.equals("off")) {
            operationState = false;
        } else {

            if (topic.equals(MOISTURE_LEVEL_TOPIC)) {
                moistureValue.setValue(receivedMessage);
                return;
            }
            //For future message like moisture, temperature
            return;
        }

        if (topic.equals(PUMP_STATE_TOPIC)) {
            Log.i("PUMP STATE", message.toString());
            irrigationSystemState.setPumpTaskRunning(operationState);
            return;
        }

        if (topic.equals(DELAYED_START_STATE_TOPIC)) {
            irrigationSystemState.setDelayedStartTaskRunning(operationState);
            // delayedIrrigationState.setValue(message.toString());
            return;
        }

        if (topic.equals(MAINTAIN_MOISTURE_TASK_STATE_TOPIC)) {
            irrigationSystemState.setMoistureMaintainTaskRunning(operationState);
            return;
        }
    }

    public IrrigationSystemMutableLiveData<IrrigationSystemState> getCurrentIrrigationSystemState() {
        return currentIrrigationSystemState;
    }

    public IrrigationSystemState getIrrigationSystemState() {
        return irrigationSystemState;
    }

    public MutableLiveData<String> getMoistureValue() {
        return moistureValue;
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
