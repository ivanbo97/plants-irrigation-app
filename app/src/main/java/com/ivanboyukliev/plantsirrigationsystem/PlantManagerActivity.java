package com.ivanboyukliev.plantsirrigationsystem;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBrokerClient;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseTopicObj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

public class PlantManagerActivity extends AppCompatActivity {

    private static BasicMqttBrokerClient mqttClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent parentIntent = getIntent();
        String brokerName = parentIntent.getExtras().getString("BrokerName");
        String brokerUrl = parentIntent.getExtras().getString("BrokerUrl");
        List<FirebaseTopicObj> brokerTopics = (List<FirebaseTopicObj>) parentIntent.getSerializableExtra("TopicsList");
        mqttClient = new BasicMqttBrokerClient(brokerUrl, brokerTopics);
        setContentView(R.layout.activity_plant_manager);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.plant_irrigation, R.id.realtime_data, R.id.irrigation_history)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public static BasicMqttBrokerClient getMqttClient() {
        return mqttClient;
    }
}