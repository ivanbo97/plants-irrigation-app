package com.ivanboyukliev.plantsirrigationsystem.topicsrecyclerview.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ivanboyukliev.plantsirrigationsystem.R;

public class TopicViewHolder extends RecyclerView.ViewHolder {

    private TextView topicNameTv;
    private FloatingActionButton deleteTopicBtn;


    public TopicViewHolder(@NonNull View itemView) {
        super(itemView);
        topicNameTv = itemView.findViewById(R.id.topicNameCardView);
        deleteTopicBtn = itemView.findViewById(R.id.deleteTopicBtn);
    }

    public TextView getTopicNameTv() {
        return topicNameTv;
    }

    public void setTopicNameTv(TextView topicNameTv) {
        this.topicNameTv = topicNameTv;
    }

    public FloatingActionButton getDeleteTopicBtn() {
        return deleteTopicBtn;
    }

    public void setDeleteTopicBtn(FloatingActionButton deleteTopicBtn) {
        this.deleteTopicBtn = deleteTopicBtn;
    }
}
