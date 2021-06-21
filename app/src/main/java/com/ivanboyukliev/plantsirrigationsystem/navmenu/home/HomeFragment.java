package com.ivanboyukliev.plantsirrigationsystem.navmenu.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.MqttAuthenticationDialog;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.api.MqttCredentialsInputListener;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseTopicObj;
import com.ivanboyukliev.plantsirrigationsystem.plantapi.PlantApiRequest;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeFragment extends Fragment implements MqttCredentialsInputListener {

    private HomeViewModel homeViewModel;
    private static String plantName;

    private TextView plantScientificName;
    private ImageView plantImageView;
    private TextView plantFamilyName;
    private TextView brokerNameTv;
    private TextView brokerUrlTv;

    private static Button connectAndSubsBtn;
    private static Button brokerDisconnectBtn;

    private static Context currentContext;

    private static PlantApiRequest plantApiRequest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        currentContext = getContext();


        Intent parentIntent = getActivity().getIntent();

        plantName = parentIntent.getExtras().getString("PlantName");

        plantApiRequest = new PlantApiRequest(plantName, getContext());

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        plantImageView = root.findViewById(R.id.plantImageHomeMenu);
        plantScientificName = root.findViewById(R.id.plantScientificName);
        plantFamilyName = root.findViewById(R.id.plantFamilyName);
        brokerNameTv = root.findViewById(R.id.currentPlantBrokerName);
        brokerUrlTv = root.findViewById(R.id.currentPlantBrokerIp);

        connectAndSubsBtn = root.findViewById(R.id.connectAndSubBrokerBtn);
        brokerDisconnectBtn = root.findViewById(R.id.disconnectBrokerBtn);
        brokerDisconnectBtn.setEnabled(false);

        String brokerName = parentIntent.getExtras().getString("BrokerName");
        String brokerUrl = parentIntent.getExtras().getString("BrokerUrl");

        List<FirebaseTopicObj> brokerTopics = (List<FirebaseTopicObj>) parentIntent.getSerializableExtra("TopicsList");

        brokerNameTv.setText(brokerName);
        brokerUrlTv.setText(brokerUrl);


        connectAndSubsBtn.setOnClickListener(v -> {
            PlantManagerActivity.getMqttClient().initClientData();
            MqttAuthenticationDialog mqttAuthDialog = new MqttAuthenticationDialog(this);
            mqttAuthDialog.show(getActivity().getSupportFragmentManager(), "MQTT Broker Authentication");

        });

        brokerDisconnectBtn.setOnClickListener(v -> {
            PlantManagerActivity.getMqttClient().disconnectClient();
        });
        homeViewModel.getCurrentPlant().observe(getViewLifecycleOwner(), plantFromApi -> {
            plantScientificName.setText(plantFromApi.getScientificName());

            Picasso.get().load(plantFromApi.getImageUrl())
                    .resize(160, 174)
                    .into(plantImageView);

            plantFamilyName.setText(plantFromApi.getFamily());
        });
        return root;
    }

    @Override
    public void onCredentialsEntered(String username, String password) {
        PlantManagerActivity.getMqttClient().connectClient(username, password);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (PlantManagerActivity.getMqttClient().getBrokerConnState().getValue()) {
            connectAndSubsBtn.setEnabled(false);
            brokerDisconnectBtn.setEnabled(true);
            return;
        }
        connectAndSubsBtn.setEnabled(true);
        brokerDisconnectBtn.setEnabled(false);
    }

    public static void showBrokerMessage(String message) {
        Toast toast = Toast.makeText(currentContext, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static String getPlantName() {
        return plantName;
    }

    public static PlantApiRequest getPlantApiRequest() {
        return plantApiRequest;
    }

    public static Button getConnectAndSubsBtn() {
        return connectAndSubsBtn;
    }

    public static Button getBrokerDisconnectBtn() {
        return brokerDisconnectBtn;
    }
}