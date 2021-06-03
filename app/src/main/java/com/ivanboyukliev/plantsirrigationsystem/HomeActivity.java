package com.ivanboyukliev.plantsirrigationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.MqttBrokerRegDialog;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.api.MqttRegDialogListener;
import com.ivanboyukliev.plantsirrigationsystem.utils.BasicMqttbroker;

public class HomeActivity extends AppCompatActivity implements MqttRegDialogListener {

    private Button logoutBtn;
    private FirebaseAuth firebaseAuth;
    private FloatingActionButton registerBrokerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        populateWidgetObjects();
        logoutBtn.setOnClickListener(v -> {
            firebaseAuth.signOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        });

        registerBrokerBtn.setOnClickListener(v -> {
            openBrokerRegisterDialog();
        });
    }

    @Override
    public void createBrokerAsWidget(BasicMqttbroker mqttbroker) {

    }

    private void populateWidgetObjects() {
        logoutBtn = findViewById(R.id.logoutBtn);
        registerBrokerBtn = findViewById(R.id.floatingActionButton);
    }

    private void openBrokerRegisterDialog() {
        MqttBrokerRegDialog mqttBrokerRegDialog = new MqttBrokerRegDialog();
        mqttBrokerRegDialog.show(getSupportFragmentManager(),"MQTT Broker Registration");
    }
}