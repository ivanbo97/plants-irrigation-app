package com.ivanboyukliev.plantsirrigationsystem.mqtt;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.api.MqttClientActions;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.home.HomeFragment;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.IrrigationSystemMutableLiveData;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.IrrigationSystemState;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata.RealtimeDataFragment;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_STATE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MAINTAIN_MOISTURE_TASK_STATE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MOISTURE_LEVEL_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PUMP_STATE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.TEMPERATURE_TOPIC;

public class AndroidMqttClientCallback implements MqttCallbackExtended {

    private MqttClientActions disconnectAction;
    private IrrigationSystemMutableLiveData<IrrigationSystemState> currentIrrigationSystemState;
    private MutableLiveData<String> moistureValue;
    private MutableLiveData<String> temperatureValue;
    private MutableLiveData<String> runningTask;
    private IrrigationSystemState irrigationSystemState;
    private String msgTopic;
    private String msgContent;

    public AndroidMqttClientCallback(MqttClientActions disconnectAction) {
        this.disconnectAction = disconnectAction;
        irrigationSystemState = new IrrigationSystemState();
        currentIrrigationSystemState = new IrrigationSystemMutableLiveData<>();
        currentIrrigationSystemState.setValue(irrigationSystemState);
        moistureValue = new MutableLiveData<>();
        temperatureValue = new MutableLiveData<>();
        runningTask = new MutableLiveData<>();
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
        Log.i(topic + ": ", message.toString());
        msgContent = message.toString();
        msgTopic = topic;
        String receivedMessage = message.toString();
        if (receivedMessage.equals("on")) {
            runningTask.setValue(msgTopic);
        }
        if (receivedMessage.equals("on") || receivedMessage.equals("off")) {
            boolean operationState;
            operationState = receivedMessage.equals("on") ? true : false;
            checkTopicAndSetSystemState(operationState);
            return;
        }

        // Received a message which is not related to pump state

        if (topic.equals(MOISTURE_LEVEL_TOPIC)) {
            RealtimeDataFragment.getMoistureChart().addEntry(Float.parseFloat(receivedMessage));
            moistureValue.setValue(receivedMessage);
            return;
        }

        if (topic.equals(TEMPERATURE_TOPIC)) {
            temperatureValue.setValue(receivedMessage);
            return;
        }

    }

    private void checkTopicAndSetSystemState(boolean operationState) {
        if (msgTopic.equals(PUMP_STATE_TOPIC)) {
            Log.i("PUMP STATE", msgContent);
            irrigationSystemState.setPumpTaskRunning(operationState);
            return;
        }

        if (msgTopic.equals(DELAYED_START_STATE_TOPIC)) {
            irrigationSystemState.setDelayedStartTaskRunning(operationState);
            // delayedIrrigationState.setValue(message.toString());
            return;
        }

        if (msgTopic.equals(MAINTAIN_MOISTURE_TASK_STATE_TOPIC)) {
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

    public MutableLiveData<String> getTemperatureValue() {
        return temperatureValue;
    }

    public MutableLiveData<String> getRunningTask() {
        return runningTask;
    }
}
