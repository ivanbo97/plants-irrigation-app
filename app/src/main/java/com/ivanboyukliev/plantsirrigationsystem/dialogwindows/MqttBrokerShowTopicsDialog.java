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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;

import com.ivanboyukliev.plantsirrigationsystem.topicsrecyclerview.adapter.TopicsRecyclerViewListAdapter;

import java.util.List;

public class MqttBrokerShowTopicsDialog extends AppCompatDialogFragment {

    private View dialogView;
    private int brokerNumber;
    private RecyclerView topicsListRecyclerView;
    private static TopicsRecyclerViewListAdapter topicsAdapter;
    private List<String> currentBrokerTopics;

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
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        topicsListRecyclerView.setLayoutManager(verticalLayoutManager);
        currentBrokerTopics = HomeActivity.getMqttBrokersList().get(brokerNumber).getTopics();
        topicsAdapter = new TopicsRecyclerViewListAdapter(currentBrokerTopics);
        topicsListRecyclerView.setAdapter(topicsAdapter);
        dialogBuilder.setView(dialogView)
                .setTitle("Topics Subscription List")
                .setNegativeButton("Cancel", (dialog, which) -> {
                    //no-action needed for now
                });
        topicsAdapter.notifyDataSetChanged();
        return dialogBuilder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

}
