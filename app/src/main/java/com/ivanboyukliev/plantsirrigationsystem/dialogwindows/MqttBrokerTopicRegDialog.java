package com.ivanboyukliev.plantsirrigationsystem.dialogwindows;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;

import com.ivanboyukliev.plantsirrigationsystem.utils.UserInputConverter;
import com.ivanboyukliev.plantsirrigationsystem.utils.UserInputValidator;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.INCORRECT_PORT_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.INCORRECT_QOS_MESSAGE;

public class MqttBrokerTopicRegDialog extends AppCompatDialogFragment {

    private View dialogView;
    private EditText topicNameTv;
    private EditText topicQoSTv;
    private String brokerID;

    public MqttBrokerTopicRegDialog(String brokerID) {
        this.brokerID = brokerID;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.mqtt_broker_topic_reg, null);
        populateDialogWidgets();
        dialogBuilder.setView(dialogView)
                .setTitle("Enter topic and its related QoS")
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("Add", null);


        return dialogBuilder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String topicName = topicNameTv.getText().toString();
            String topicQoSStr = topicQoSTv.getText().toString();
            try {
                int topicQoS = Integer.valueOf(topicQoSStr);
                if (!UserInputValidator.isQoSValid(topicQoSStr)) {
                    topicQoSTv.setError(INCORRECT_PORT_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                HomeActivity.showBrokerError(INCORRECT_QOS_MESSAGE);
                e.printStackTrace();
                return;
            }

            DatabaseReference databaseBrokerTopics = HomeActivity.getmDatabaseAuthUserBrokers().child(brokerID + "/topics");
            String topicID = UserInputConverter.convertBrokerTopicToFirebaseRules(topicName);
            databaseBrokerTopics.child(topicID).setValue(Long.valueOf(topicQoSStr));
            dialog.dismiss();
        });
    }

    private void populateDialogWidgets() {
        topicNameTv = dialogView.findViewById(R.id.mqtt_broker_new_topic);
        topicQoSTv = dialogView.findViewById(R.id.mqtt_broker_topic_qos);
    }
}