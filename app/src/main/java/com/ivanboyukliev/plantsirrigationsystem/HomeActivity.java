package com.ivanboyukliev.plantsirrigationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.adapter.BrokersRecyclerViewListAdapter;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.MqttBrokerRegDialog;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.api.BrokerDataInputListener;
import com.ivanboyukliev.plantsirrigationsystem.firebase.BrokerDataChangeListener;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseBroker;
import com.ivanboyukliev.plantsirrigationsystem.utils.AndroidUIManager;

import java.util.ArrayList;
import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DB_URL;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.NAV_BAR_INVISIBLE;

public class HomeActivity extends AppCompatActivity implements BrokerDataInputListener {

    private Button logoutBtn;
    private FirebaseAuth firebaseAuth;
    private static DatabaseReference databaseAuthUserBrokers;
    private FloatingActionButton registerBrokerBtn;
    private RecyclerView brokersListRecyclerView;
    private static BrokersRecyclerViewListAdapter brokersAdapter;
    private static AndroidUIManager uiManager;
    private static List<FirebaseBroker> mqttBrokers;
    private static Context homeActivityContext;
    private static FragmentManager homeActivityFragmentManager;

    @Override
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiManager = new AndroidUIManager(getWindow());
        homeActivityContext = getApplicationContext();
        homeActivityFragmentManager = getSupportFragmentManager();
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
        databaseAuthUserBrokers = FirebaseDatabase.getInstance(DB_URL)
                .getReference("users/" + firebaseAuth.getUid() + "/brokers");
        databaseAuthUserBrokers.addListenerForSingleValueEvent(new BrokerDataChangeListener());
    }

    @Override
    public void onBrokerDataSending() {
        brokersAdapter.notifyDataSetChanged();
    }

    private void populateWidgetObjects() {
        logoutBtn = findViewById(R.id.logoutBtn);
        registerBrokerBtn = findViewById(R.id.addNewBrokerButton);
        brokersListRecyclerView = findViewById(R.id.brokersListRecyclerView);
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

    public static BrokersRecyclerViewListAdapter getBrokersAdapter() {
        return brokersAdapter;
    }

    public static List<FirebaseBroker> getMqttBrokersList() {
        return mqttBrokers;
    }

    public static AndroidUIManager getUiManager() {
        return uiManager;
    }

    public static Context getHomeActivityContext() {
        return homeActivityContext;
    }

    public static void showBrokerMessage(String message) {
        Toast toast = Toast.makeText(homeActivityContext, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static FragmentManager getHomeActivityFragmentManager() {
        return homeActivityFragmentManager;
    }

    public static DatabaseReference getDatabaseAuthUserBrokers() {
        return databaseAuthUserBrokers;
    }
}