package com.ivanboyukliev.plantsirrigationsystem.topicsrecyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;

import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseTopicObj;
import com.ivanboyukliev.plantsirrigationsystem.topicsrecyclerview.viewholder.TopicViewHolder;
import com.ivanboyukliev.plantsirrigationsystem.utils.UserInputConverter;

import java.util.List;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.BROKER_DEL_ERROR;


public class TopicsRecyclerViewListAdapter extends RecyclerView.Adapter<TopicViewHolder> {

    private List<FirebaseTopicObj> mqttBrokerTopics;
    private String brokerID;
    private int brokerNumInList;

    public TopicsRecyclerViewListAdapter(List<FirebaseTopicObj> mqttBrokerTopics, String brokerID, int brokerNumInList) {
        this.mqttBrokerTopics = mqttBrokerTopics;
        this.brokerID = brokerID;
        this.brokerNumInList = brokerNumInList;
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
        String topicID = currentTopic.getTopicName();
        String firebaseTopicID = UserInputConverter.convertBrokerTopicToFirebaseRules(topicID);
        holder.getTopicNameTv().setText(currentTopic.getTopicName());
        String qosTopicContent = "QoS level : " + String.valueOf(currentTopic.getQoS());
        holder.getTopicQoSTv().setText(qosTopicContent);
        holder.getDeleteTopicBtn().setOnClickListener(v -> {
            DatabaseReference currentTopicDB = HomeActivity.getmDatabaseAuthUserBrokers().child(brokerID + "/topics/" + firebaseTopicID);
            if (!HomeActivity.getMqttBrokersList().get(brokerNumInList).isConnected()) {
                HomeActivity.showBrokerMessage(BROKER_DEL_ERROR);
                return;
            }
            currentTopicDB.removeValue((error, ref) -> {
                if (error == null) {
                    mqttBrokerTopics.remove(position);
                    notifyDataSetChanged();
                }
            });
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
