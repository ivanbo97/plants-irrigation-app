package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.AndroidMqttClientCallback;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.DelayedPumpStartWidgets;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.IrrigationSystemState;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.MoistureManagementWidgets;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners.PumpSwitchStateListener;

import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;


public class PlantIrrigationFragment extends Fragment {

    private PlantIrrigationViewModel plantIrrigationViewModel;
    private Switch pumpManager;
    private DelayedPumpStartWidgets delayedStartWidgetsManager;
    private MoistureManagementWidgets moistureManagementWidgets;
    private PumpSwitchStateListener pumpSwitchStateListener;

    private IrrigationSystemState currentIrrigationSystemState;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        plantIrrigationViewModel =
                new ViewModelProvider(this).get(PlantIrrigationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_irrigation, container, false);

        final TextView connectionStateTv = root.findViewById(R.id.connStatusTv);

        delayedStartWidgetsManager = new DelayedPumpStartWidgets(root, getActivity());
        moistureManagementWidgets = new MoistureManagementWidgets(root, this);

        PlantManagerActivity.getMqttClient().getBrokerConnState().observe(getViewLifecycleOwner(), connected -> {
            if (connected) {
                connectionStateTv.setText("Connected");
                currentIrrigationSystemState.setConnectedToBroker(true);
                return;
            }
            connectionStateTv.setText("Disconnected");
            if (currentIrrigationSystemState == null)
                return;
            currentIrrigationSystemState.setConnectedToBroker(false);
        });

        pumpManager = root.findViewById(R.id.pumpSwitch);

        pumpSwitchStateListener = new PumpSwitchStateListener(this);

        pumpManager.setOnCheckedChangeListener(pumpSwitchStateListener);

        MqttCallbackExtended mqttCallback = PlantManagerActivity.getMqttClient().getMqttCallback();

        if (mqttCallback == null)
            return root;

        currentIrrigationSystemState = ((AndroidMqttClientCallback) PlantManagerActivity.getMqttClient()
                .getMqttCallback())
                .getIrrigationSystemState();

        ((AndroidMqttClientCallback) mqttCallback).getCurrentIrrigationSystemState().observe(getViewLifecycleOwner(), irrigationSystemState -> {

            if (!currentIrrigationSystemState.isConnectedToBroker()) {
                setWidgetsActiveness(false);
                return;
            }

            // Possible system state 1 - delayed start task is currently running
            if (irrigationSystemState.isDelayedStartTaskRunning()) {
                pumpManager.setEnabled(false);
                moistureManagementWidgets.setWidgetsActive(false);
                delayedStartWidgetsManager.getTerminateDelayedStartBtn().setEnabled(true);
                delayedStartWidgetsManager.getSubmitDelayedStartBtn().setEnabled(false);
                return;
            }

            // Possible system state 2 - moisture maintaining task is currently running
            if (irrigationSystemState.isMoistureMaintainTaskRunning()) {
                pumpManager.setEnabled(false);
                delayedStartWidgetsManager.setWidgetsActive(false);
                moistureManagementWidgets.getTerminateMoistureTaskBtn().setEnabled(true);
                moistureManagementWidgets.getSubmitMoistureBtn().setEnabled(false);
                return;
            }

            // Possible system state 3 - pump is currently running because the user has turned it on manually
            boolean isPumpOn = irrigationSystemState.isPumpTaskRunning();
            boolean isDelayedStTaskOn = irrigationSystemState.isMoistureMaintainTaskRunning();
            boolean isMoistureMaintainTaskOn = irrigationSystemState.isDelayedStartTaskRunning();
            if (isPumpOn && !isDelayedStTaskOn && !isMoistureMaintainTaskOn) {
                delayedStartWidgetsManager.setWidgetsActive(false);
                moistureManagementWidgets.setWidgetsActive(false);
                pumpManager.setEnabled(true);
                return;
            }

            // Possible system state 4 - no task is currently running
            setWidgetsActiveness(true);
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        // If connection to broker is lost, it will be wrong to check for system state
        if (!PlantManagerActivity.getMqttClient().getBrokerConnState().getValue()) {
            return;
        }

        // Restore consistent state of widgets according to current running tasks
        if (currentIrrigationSystemState.isPumpTaskRunning()) {
            //Bypassing the the execution of switch event handler
            pumpManager.setOnCheckedChangeListener(null);
            pumpManager.setChecked(true);
            pumpManager.setOnCheckedChangeListener(pumpSwitchStateListener);
            return;
        }

        if (currentIrrigationSystemState.isMoistureMaintainTaskRunning()) {
            moistureManagementWidgets.getSubmitMoistureBtn().setEnabled(false);
            moistureManagementWidgets.getTerminateMoistureTaskBtn().setEnabled(true);
            return;
        }

        if (currentIrrigationSystemState.isDelayedStartTaskRunning()) {
            delayedStartWidgetsManager.getSubmitDelayedStartBtn().setEnabled(false);
            delayedStartWidgetsManager.getTerminateDelayedStartBtn().setEnabled(true);
            return;
        }

        //No task is running
        setWidgetsActiveness(true);
    }

    private void setWidgetsActiveness(boolean active) {
        RelativeLayout plantIrrigRelativeLayout = getActivity().findViewById(R.id.plantIrrigationLayoutManager);
        for (int i = 0; i < plantIrrigRelativeLayout.getChildCount(); i++) {
            View view = plantIrrigRelativeLayout.getChildAt(i);
            int viewId = view.getId();
            if (viewId == R.id.terminateMoistureTaskBtn || viewId == R.id.terminateDelayedStartBtn || viewId == R.id.connStatusTitleTv || viewId == R.id.connStatusTv) {
                continue;
            }
            view.setEnabled(active);
        }
    }
}