package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.dialogpickers.DatePickerFragment;
import com.ivanboyukliev.plantsirrigationsystem.dialogpickers.TimePickerFragment;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.AndroidMqttClientCallback;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.IrrigationSystemState;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners.MaintainMoistureBtnListener;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners.PumpSwitchStateListener;

import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;

import java.util.ArrayList;
import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_FLAG;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MOISTURE_MAINTAIN_FLAG;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PUMP_ACTIVE_FLAG;


public class PlantIrrigationFragment extends Fragment {

    private PlantIrrigationViewModel plantIrrigationViewModel;

    private static TextView inputDateTv;

    private static TextView inputTimeTv;

    private Button setDateBtn;
    private Button setTimeBtn;
    private Button submitDelayedStartBtn;
    private Button submitMoistureBtn;
    private Switch pumpManager;
    private static EditText enteredMoisture;

    private List<TextView> currentWidgets;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        currentWidgets = new ArrayList<>();
        plantIrrigationViewModel =
                new ViewModelProvider(this).get(PlantIrrigationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_irrigation, container, false);
        final TextView connectionStateTv = root.findViewById(R.id.connStatusTv);

        inputDateTv = root.findViewById(R.id.chosenDateTv);

        inputTimeTv = root.findViewById(R.id.chosenTimeTv);

        PlantManagerActivity.getMqttClient().getBrokerConnState().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    connectionStateTv.setText("Connected");
                    activateWidgets(true);
                    return;
                }
                connectionStateTv.setText("Disconnected");
                activateWidgets(false);
            }
        });

        setDateBtn = root.findViewById(R.id.pickUpDateBtn);
        setTimeBtn = root.findViewById(R.id.pickUpTimeBtn);
        submitDelayedStartBtn = root.findViewById(R.id.submitDelayStart);
        pumpManager = root.findViewById(R.id.pumpSwitch);
        submitMoistureBtn = root.findViewById(R.id.submitMoistureBtn);
        enteredMoisture = root.findViewById(R.id.inputMoisture);

        addWidgetToList(setDateBtn);
        addWidgetToList(setTimeBtn);
        addWidgetToList(submitDelayedStartBtn);
        addWidgetToList(pumpManager);
        addWidgetToList(submitMoistureBtn);
        addWidgetToList(enteredMoisture);

        IrrigationSystemState irrigationSystemState = new IrrigationSystemState();

        PumpSwitchStateListener pumpSwitchStateListener = new PumpSwitchStateListener(irrigationSystemState, getContext());

        pumpManager.setOnCheckedChangeListener(pumpSwitchStateListener);

        setDateBtn.setOnClickListener(v -> {
            DialogFragment dateDialog = new DatePickerFragment(R.layout.date_picker_spinner);
            dateDialog.show(getActivity().getSupportFragmentManager(), "DatePicker");
        });


        setTimeBtn.setOnClickListener(v -> {
            DialogFragment timeDialog = new TimePickerFragment();
            timeDialog.show(getActivity().getSupportFragmentManager(), "TimePicker");
        });

        MaintainMoistureBtnListener maintainMoistureBtnListener = new MaintainMoistureBtnListener(irrigationSystemState, getActivity());
        submitMoistureBtn.setOnClickListener(maintainMoistureBtnListener);

        MqttCallbackExtended mqttCallback = PlantManagerActivity.getMqttClient().getMqttCallback();

        if (mqttCallback == null)
            return root;

        ((AndroidMqttClientCallback) mqttCallback).getReceivedPumpState().observe(getViewLifecycleOwner(), s -> {
            if (s.equals(PUMP_ACTIVE_FLAG)) {
                irrigationSystemState.setPumpActive(true);
                return;
            }
            irrigationSystemState.setPumpActive(false);
        });

        ((AndroidMqttClientCallback) mqttCallback).getDelayedIrrigationState().observe(getViewLifecycleOwner(), s -> {
            if (s.equals(DELAYED_START_FLAG)) {
                irrigationSystemState.setDelayedStartTaskOn(true);
                return;
            }
            irrigationSystemState.setDelayedStartTaskOn(false);
        });


        ((AndroidMqttClientCallback) mqttCallback).getMoistureMaintainingOperationState().observe(getViewLifecycleOwner(), s -> {
            if (s.equals(MOISTURE_MAINTAIN_FLAG)) {
                irrigationSystemState.setMoistureMaintainTaskOn(true);
                return;
            }
            irrigationSystemState.setMoistureMaintainTaskOn(false);
        });

        return root;
    }

    private void activateWidgets(boolean state) {
        for (TextView widget : currentWidgets) {
            widget.setEnabled(state);
        }
    }

    private void addWidgetToList(TextView widget) {
        currentWidgets.add(widget);
    }

    public static TextView getInputDateTv() {
        return inputDateTv;
    }

    public static TextView getInputTimeTv() {
        return inputTimeTv;
    }

    public static EditText getEnteredMoisture() {
        return enteredMoisture;
    }
}