package com.ivanboyukliev.plantsirrigationsystem.topicsrecyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanboyukliev.plantsirrigationsystem.R;

import com.ivanboyukliev.plantsirrigationsystem.topicsrecyclerview.viewholder.TopicViewHolder;

import java.util.List;


public class TopicsRecyclerViewListAdapter extends RecyclerView.Adapter<TopicViewHolder> {

    private List<String> mqttBrokerTopics;

    public TopicsRecyclerViewListAdapter(List<String> mqttBrokerTopics) {
        this.mqttBrokerTopics = mqttBrokerTopics;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View topicRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_list_row, parent, false);
        TopicViewHolder topicViewHolder = new TopicViewHolder(topicRowView);
        return topicViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        String topicName = mqttBrokerTopics.get(position);
        holder.getTopicNameTv().setText(topicName);
        holder.getDeleteTopicBtn().setOnClickListener(v -> {
            //Delete topic logic
        });
    }

    @Override
    public int getItemCount() {
        return mqttBrokerTopics.size();
    }
}
