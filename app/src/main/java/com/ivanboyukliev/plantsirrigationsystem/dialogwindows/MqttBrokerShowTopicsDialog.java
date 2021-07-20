package com.ivanboyukliev.plantsirrigationsystem.dialogwindows;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseBroker;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseTopicObj;
import com.ivanboyukliev.plantsirrigationsystem.topicsrecyclerview.adapter.TopicsRecyclerViewListAdapter;

import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.TOPICS_LIST_TITLE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.TOPIC_ADD_BTN_TXT;

public class MqttBrokerShowTopicsDialog extends AppCompatDialogFragment {

    private View dialogView;
    private int brokerNumber;
    private RecyclerView topicsListRecyclerView;
    private static TopicsRecyclerViewListAdapter topicsAdapter;
    private List<FirebaseTopicObj> currentBrokerTopics;

    public MqttBrokerShowTopicsDialog(int brokerNumber) {
        this.brokerNumber = brokerNumber;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        dialogView = inflater.inflate(R.layout.mqtt_broker_topics_dialog, null);
        topicsListRecyclerView = dialogView.findViewById(R.id.topicsListRecyclerView);

        topicsListRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        topicsListRecyclerView.setLayoutManager(verticalLayoutManager);

        FirebaseBroker currentMqttBroker = HomeActivity.getMqttBrokersList().get(brokerNumber);
        currentBrokerTopics = currentMqttBroker.getTopics();
        String brokerID = currentMqttBroker.getBrokerID();
        topicsAdapter = new TopicsRecyclerViewListAdapter(currentBrokerTopics, brokerID, brokerNumber);
        topicsListRecyclerView.setAdapter(topicsAdapter);

        dialogBuilder.setView(dialogView)
                .setTitle(TOPICS_LIST_TITLE)
                .setNegativeButton(TOPIC_ADD_BTN_TXT, (dialog, which) -> {
                    openTopicRegisterDialog(getParentFragmentManager());
                });
        topicsAdapter.notifyDataSetChanged();
        return dialogBuilder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void openTopicRegisterDialog(FragmentManager fragmentManager) {
        String brokerID = HomeActivity.getMqttBrokersList().get(brokerNumber).getBrokerID();
        MqttBrokerTopicRegDialog mqttBrokerTopicRegDialog = new MqttBrokerTopicRegDialog(brokerID, brokerNumber);
        mqttBrokerTopicRegDialog.show(fragmentManager, "MQTT Broker Registration");
    }
}
