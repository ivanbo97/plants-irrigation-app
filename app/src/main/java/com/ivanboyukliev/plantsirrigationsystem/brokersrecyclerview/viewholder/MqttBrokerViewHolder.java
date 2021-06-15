package com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ivanboyukliev.plantsirrigationsystem.R;

public class MqttBrokerViewHolder extends RecyclerView.ViewHolder {

    private TextView brokerNameTv;
    private EditText brokerIpTv;
    private ImageButton editBrokerDataBtn;
    private Button saveChangesBtn;
    private FloatingActionButton deleteBrokerButton;
    private TextView showTopicsTv;
    private TextView showPlantsTv;


    public MqttBrokerViewHolder(@NonNull View itemView) {
        super(itemView);
        brokerNameTv = itemView.findViewById(R.id.brokerNameTv);
        brokerIpTv = itemView.findViewById(R.id.brokerIP);
        brokerIpTv.setTag(brokerIpTv.getKeyListener());
        brokerIpTv.setKeyListener(null);
        deleteBrokerButton = itemView.findViewById(R.id.deleteBrokerButton);
        showTopicsTv = itemView.findViewById(R.id.showTopicsTv);
        showPlantsTv = itemView.findViewById(R.id.showPlantsTv);
        editBrokerDataBtn = itemView.findViewById(R.id.editBrokerDataBtn);
        saveChangesBtn = itemView.findViewById(R.id.saveBrokerChangesBtn);
    }

    public TextView getBrokerNameTv() {
        return brokerNameTv;
    }

    public void setBrokerNameTv(TextView brokerNameTv) {
        this.brokerNameTv = brokerNameTv;
    }

    public EditText getBrokerIpTv() {
        return brokerIpTv;
    }

    public void setBrokerIpTv(EditText brokerIpTv) {
        this.brokerIpTv = brokerIpTv;
    }

    public FloatingActionButton getDeleteBrokerButton() {
        return deleteBrokerButton;
    }

    public void setDeleteBrokerButton(FloatingActionButton deleteBrokerButton) {
        this.deleteBrokerButton = deleteBrokerButton;
    }

    public TextView getShowTopicsTv() {
        return showTopicsTv;
    }

    public void setShowTopicsTv(TextView showTopicsTv) {
        this.showTopicsTv = showTopicsTv;
    }

    public TextView getShowPlantsTv() {
        return showPlantsTv;
    }

    public void setShowPlantsTv(TextView showPlantsTv) {
        this.showPlantsTv = showPlantsTv;
    }

    public ImageButton getEditBrokerDataBtn() {
        return editBrokerDataBtn;
    }

    public void setEditBrokerDataBtn(ImageButton editBrokerDataBtn) {
        this.editBrokerDataBtn = editBrokerDataBtn;
    }

    public Button getSaveChangesBtn() {
        return saveChangesBtn;
    }

    public void setSaveChangesBtn(Button saveChangesBtn) {
        this.saveChangesBtn = saveChangesBtn;
    }
}
