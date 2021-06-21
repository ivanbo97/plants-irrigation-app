package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;

public class PlantIrrigationFragment extends Fragment {

    private PlantIrrigationViewModel plantIrrigationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        plantIrrigationViewModel =
                new ViewModelProvider(this).get(PlantIrrigationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_irrigation, container, false);
        final TextView connectionStateTv = root.findViewById(R.id.connStatusTv);

        PlantManagerActivity.getMqttClient().getBrokerConnState().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    connectionStateTv.setText("Connected");
                    return;
                }
                connectionStateTv.setText("Disconnected");
            }
        });
        return root;
    }
}