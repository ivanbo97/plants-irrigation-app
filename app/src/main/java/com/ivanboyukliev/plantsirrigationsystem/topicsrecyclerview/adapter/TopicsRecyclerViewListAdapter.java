package com.ivanboyukliev.plantsirrigationsystem.topicsrecyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ivanboyukliev.plantsirrigationsystem.R;

import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseTopicObj;
import com.ivanboyukliev.plantsirrigationsystem.topicsrecyclerview.viewholder.TopicViewHolder;

import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DB_URL;


public class TopicsRecyclerViewListAdapter extends RecyclerView.Adapter<TopicViewHolder> {

    private List<FirebaseTopicObj> mqttBrokerTopics;
    private String brokerID;

    public TopicsRecyclerViewListAdapter(List<FirebaseTopicObj> mqttBrokerTopics, String brokerID) {
        this.mqttBrokerTopics = mqttBrokerTopics;
        this.brokerID = brokerID;
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
        FirebaseTopicObj currentTopic = mqttBrokerTopics.get(position);
        String currentTopicID = currentTopic.getTopicID();
        holder.getTopicNameTv().setText(currentTopic.getTopicName());
        holder.getDeleteTopicBtn().setOnClickListener(v -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            DatabaseReference currentTopicDB = FirebaseDatabase.getInstance(DB_URL).getReference(
                    "users/" + firebaseAuth.getUid() + "/" + brokerID
                            + "/topics/" + currentTopicID);
            currentTopicDB.removeValue();
            mqttBrokerTopics.remove(position);
            notifyDataSetChanged();

        });
    }

    @Override
    public int getItemCount() {
        return mqttBrokerTopics.size();
    }

    public String getBrokerID() {
        return brokerID;
    }

    public void setBrokerID(String brokerID) {
        this.brokerID = brokerID;
    }
}
