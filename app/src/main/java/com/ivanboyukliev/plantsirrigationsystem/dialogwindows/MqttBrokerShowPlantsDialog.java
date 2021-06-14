package com.ivanboyukliev.plantsirrigationsystem.dialogwindows;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBrokerClient;
import com.ivanboyukliev.plantsirrigationsystem.currentplantsrecyclerview.adapter.BrokerPlantsRecyclerViewListAdapter;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebasePlantObj;


import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PLANTS_LIST_TITLE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PLANT_ADD_BTN_TXT;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PLANT_INFO;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PLANT_REG_ERROR;

public class MqttBrokerShowPlantsDialog extends AppCompatDialogFragment {

    private int brokerNumInList;
    private View dialogView;
    private RecyclerView plantsListRecyclerView;
    private static BrokerPlantsRecyclerViewListAdapter topicsAdapter;
    private List<FirebasePlantObj> currentBrokerPlants;

    public MqttBrokerShowPlantsDialog(int brokerNumInList) {
        this.brokerNumInList = brokerNumInList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        dialogView = inflater.inflate(R.layout.mqtt_broker_plants_dialog, null);
        plantsListRecyclerView = dialogView.findViewById(R.id.plantsListRecyclerView);

        plantsListRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        plantsListRecyclerView.setLayoutManager(verticalLayoutManager);

        BasicMqttBrokerClient currentMqttBroker = HomeActivity.getMqttBrokersList().get(brokerNumInList);
        currentBrokerPlants = currentMqttBroker.getPlants();
        String brokerID = currentMqttBroker.getBrokerID();
        topicsAdapter = new BrokerPlantsRecyclerViewListAdapter(currentBrokerPlants, brokerID, brokerNumInList);
        plantsListRecyclerView.setAdapter(topicsAdapter);

        dialogBuilder.setView(dialogView)
                .setTitle(PLANTS_LIST_TITLE)
                .setNegativeButton(PLANT_ADD_BTN_TXT, (dialog, which) -> {
                    if (!HomeActivity.getMqttBrokersList().get(brokerNumInList).isConnected()) {
                        Toast.makeText(getContext(), PLANT_REG_ERROR, Toast.LENGTH_SHORT).show();
                        return;
                    }
                   // openTopicRegisterDialog(getParentFragmentManager());
                })
                .setPositiveButton(PLANT_INFO, (dialog, which) -> {
                   // currentMqttBroker.subscribeToTopics();
                    return;
                });
        topicsAdapter.notifyDataSetChanged();
        return dialogBuilder.create();

    }
}
