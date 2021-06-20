package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.AndroidMqttClientCallback;

public class PlantIrrigationFragment extends Fragment {

    private PlantIrrigationViewModel plantIrrigationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        plantIrrigationViewModel =
                new ViewModelProvider(this).get(PlantIrrigationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_irrigation, container, false);
        final TextView textView = root.findViewById(R.id.text_irrigation);
        plantIrrigationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        ((AndroidMqttClientCallback) PlantManagerActivity.getMqttClient().getMqttCallback()).getReceivedData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}