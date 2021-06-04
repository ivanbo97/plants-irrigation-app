package com.ivanboyukliev.plantsirrigationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.adapter.BrokersRecyclerViewListAdapter;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.MqttBrokerRegDialog;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.api.MqttRegDialogListener;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBroker;
import com.ivanboyukliev.plantsirrigationsystem.utils.AndroidUIManager;

import java.util.ArrayList;
import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.NAV_BAR_INVISIBLE;

public class HomeActivity extends AppCompatActivity implements MqttRegDialogListener {

    private Button logoutBtn;
    private FloatingActionButton deleteBrokerButton;
    private FirebaseAuth firebaseAuth;
    private FloatingActionButton registerBrokerBtn;
    private RecyclerView brokersListRecyclerView;
    private BrokersRecyclerViewListAdapter brokersAdapter;
    private AndroidUIManager uiManager;
    private static List<BasicMqttBroker> mqttBrokers;


    @Override
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiManager = new AndroidUIManager(getWindow());
        uiManager.disableNavigationBar();
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        mqttBrokers = new ArrayList<>();
        populateWidgetObjects();
        brokersListRecyclerView.addItemDecoration(new DividerItemDecoration(HomeActivity.this, LinearLayout.VERTICAL));
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
        brokersListRecyclerView.setLayoutManager(verticalLayoutManager);
        brokersAdapter = new BrokersRecyclerViewListAdapter(mqttBrokers);
        brokersListRecyclerView.setAdapter(brokersAdapter);
        logoutBtn.setOnClickListener(v -> {
            firebaseAuth.signOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        });

        registerBrokerBtn.setOnClickListener(v -> openBrokerRegisterDialog());

    }


    @Override
    public void onDialogDataSending() {
        brokersAdapter.notifyDataSetChanged();
    }

    private void populateWidgetObjects() {
        logoutBtn = findViewById(R.id.logoutBtn);
        registerBrokerBtn = findViewById(R.id.addNewBrokerButton);
        brokersListRecyclerView = findViewById(R.id.brokersListRecyclerView);
        deleteBrokerButton = findViewById(R.id.deleteBrokerButton);
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(NAV_BAR_INVISIBLE);
        }
    }

    private void openBrokerRegisterDialog() {
        MqttBrokerRegDialog mqttBrokerRegDialog = new MqttBrokerRegDialog();
        mqttBrokerRegDialog.show(getSupportFragmentManager(), "MQTT Broker Registration");
    }

    public BrokersRecyclerViewListAdapter getBrokersAdapter() {
        return brokersAdapter;
    }

    public static List<BasicMqttBroker> getMqttBrokersList() {
        return mqttBrokers;
    }


}