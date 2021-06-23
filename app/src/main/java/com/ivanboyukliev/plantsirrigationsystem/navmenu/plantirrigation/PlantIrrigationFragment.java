package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.MoistureManagementWidgets;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners.PumpSwitchStateListener;

import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;

import java.util.ArrayList;
import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_INIT_FLAG;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MOISTURE_MAINTAIN_FLAG;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PUMP_ACTIVE_FLAG;


public class PlantIrrigationFragment extends Fragment {

    private PlantIrrigationViewModel plantIrrigationViewModel;
    private Switch pumpManager;
    private DelayedPumpStartWidgets delayedStartWidgetsManager;
    private MoistureManagementWidgets moistureManagementWidgets;


    private List<TextView> currentWidgets;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        currentWidgets = new ArrayList<>();
        plantIrrigationViewModel =
                new ViewModelProvider(this).get(PlantIrrigationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_irrigation, container, false);
        final TextView connectionStateTv = root.findViewById(R.id.connStatusTv);

        delayedStartWidgetsManager = new DelayedPumpStartWidgets(root, getActivity());
        moistureManagementWidgets = new MoistureManagementWidgets(root, this);

        PlantManagerActivity.getMqttClient().getBrokerConnState().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    connectionStateTv.setText("Connected");
                    pumpManager.setEnabled(true);
                    delayedStartWidgetsManager.setWidgetsActive(true);
                    moistureManagementWidgets.setWidgetsActive(true);
                    //Terminate operation buttons should be active only when operation is active
                    delayedStartWidgetsManager.getTerminateDelayedStartBtn().setEnabled(false);
                    moistureManagementWidgets.getTerminateMoistureTaskBtn().setEnabled(false);
                    return;
                }
                connectionStateTv.setText("Disconnected");
                pumpManager.setEnabled(false);
                delayedStartWidgetsManager.setWidgetsActive(false);
                delayedStartWidgetsManager.getTerminateDelayedStartBtn().setEnabled(false);
                moistureManagementWidgets.setWidgetsActive(false);
                moistureManagementWidgets.getTerminateMoistureTaskBtn().setEnabled(false);
            }
        });

        pumpManager = root.findViewById(R.id.pumpSwitch);

        PumpSwitchStateListener pumpSwitchStateListener = new PumpSwitchStateListener(this);

        pumpManager.setOnCheckedChangeListener(pumpSwitchStateListener);

        MqttCallbackExtended mqttCallback = PlantManagerActivity.getMqttClient().getMqttCallback();

        if (mqttCallback == null)
            return root;

        ((AndroidMqttClientCallback) mqttCallback).getReceivedPumpState().observe(getViewLifecycleOwner(), s -> {
            if (s.equals(PUMP_ACTIVE_FLAG)) {
                moistureManagementWidgets.setWidgetsActive(false);
                delayedStartWidgetsManager.setWidgetsActive(false);
                return;
            }
            //If pump is off, activate other widgets
            moistureManagementWidgets.setWidgetsActive(true);
            delayedStartWidgetsManager.setWidgetsActive(true);
        });

        ((AndroidMqttClientCallback) mqttCallback).getDelayedIrrigationState().observe(getViewLifecycleOwner(), s -> {
            if (s.equals(DELAYED_START_INIT_FLAG)) {
                pumpManager.setEnabled(false);
                moistureManagementWidgets.setWidgetsActive(false);
                return;
            }
            pumpManager.setEnabled(true);
            moistureManagementWidgets.setWidgetsActive(true);
        });


        ((AndroidMqttClientCallback) mqttCallback).getMoistureMaintainingOperationState().observe(getViewLifecycleOwner(), s -> {
            if (s.equals(MOISTURE_MAINTAIN_FLAG)) {
                pumpManager.setEnabled(false);
                delayedStartWidgetsManager.setWidgetsActive(false);
                return;
            }
            pumpManager.setEnabled(true);
            delayedStartWidgetsManager.setWidgetsActive(true);
        });
        return root;
    }
}