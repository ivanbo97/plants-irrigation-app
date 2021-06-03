package com.ivanboyukliev.plantsirrigationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.adapter.BrokersRecyclerViewListAdapter;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.MqttBrokerRegDialog;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.api.MqttRegDialogListener;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBroker;

public class HomeActivity extends AppCompatActivity implements MqttRegDialogListener {

    private Button logoutBtn;
    private FirebaseAuth firebaseAuth;
    private FloatingActionButton registerBrokerBtn;
    private RecyclerView brokersListRecyclerView;
    private BrokersRecyclerViewListAdapter brokersAdapter;
    private static BasicMqttBroker newMqttBroker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        newMqttBroker = new BasicMqttBroker();
        populateWidgetObjects();
        brokersListRecyclerView.addItemDecoration(new DividerItemDecoration(HomeActivity.this, LinearLayout.VERTICAL));
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
        brokersListRecyclerView.setLayoutManager(verticalLayoutManager);
        brokersAdapter = new BrokersRecyclerViewListAdapter(newMqttBroker);
        brokersListRecyclerView.setAdapter(brokersAdapter);
        logoutBtn.setOnClickListener(v -> {
            firebaseAuth.signOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        });

        registerBrokerBtn.setOnClickListener(v -> {
            openBrokerRegisterDialog();
        });
    }

    @Override
    public void onDialogDataSending() {
        brokersAdapter.notifyDataSetChanged();
    }

    private void populateWidgetObjects() {
        logoutBtn = findViewById(R.id.logoutBtn);
        registerBrokerBtn = findViewById(R.id.deleteBrokerButton);
        brokersListRecyclerView = findViewById(R.id.brokersListRecyclerView);
    }

    private void openBrokerRegisterDialog() {
        MqttBrokerRegDialog mqttBrokerRegDialog = new MqttBrokerRegDialog();
        mqttBrokerRegDialog.show(getSupportFragmentManager(), "MQTT Broker Registration");
    }

    public BrokersRecyclerViewListAdapter getBrokersAdapter() {
        return brokersAdapter;
    }

    public static BasicMqttBroker getNewMqttBroker() {
        return newMqttBroker;
    }
}