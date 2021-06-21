package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class PlantIrrigationFragment extends Fragment {

    private PlantIrrigationViewModel plantIrrigationViewModel;

    private static TextView inputDateTv;

    private static TextView inputTimeTv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
                    return;
                }
                connectionStateTv.setText("Disconnected");
            }
        });

        Button setDateBtn = root.findViewById(R.id.pickUpDateBtn);
        setDateBtn.setOnClickListener(v -> {
            DialogFragment dateDialog = new DatePickerFragment(R.layout.date_picker_spinner);
            dateDialog.show(getActivity().getSupportFragmentManager(), "DatePicker");
        });

        Button setTimeBtn = root.findViewById(R.id.pickUpTimeBtn);

        setTimeBtn.setOnClickListener(v -> {
            DialogFragment timeDialog = new TimePickerFragment();
            timeDialog.show(getActivity().getSupportFragmentManager(), "TimePicker");
        });
        return root;
    }

    public static TextView getInputDateTv() {
        return inputDateTv;
    }

    public static TextView getInputTimeTv() {
        return inputTimeTv;
    }
}