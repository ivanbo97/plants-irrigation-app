package com.ivanboyukliev.plantsirrigationsystem.dialogwindows;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseTopicObj;
import com.ivanboyukliev.plantsirrigationsystem.utils.UserInputConverter;
import com.ivanboyukliev.plantsirrigationsystem.utils.UserInputValidator;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.INCORRECT_PORT_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.INCORRECT_QOS_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.TOPIC_REG_DIALOG_TITLE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.TOPIC_REG_ERROR;

public class MqttBrokerTopicRegDialog extends AppCompatDialogFragment {

    private View dialogView;
    private EditText topicNameTv;
    private EditText topicQoSTv;
    private String brokerID;
    private int brokerNumInList;

    public MqttBrokerTopicRegDialog(String brokerID, int brokerNumInList) {
        this.brokerID = brokerID;
        this.brokerNumInList = brokerNumInList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.mqtt_broker_topic_reg, null);
        populateDialogWidgets();
        dialogBuilder.setView(dialogView)
                .setTitle(TOPIC_REG_DIALOG_TITLE)
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
                if (!UserInputValidator.isQoSValid(topicQoSStr)) {
                    topicQoSTv.setError(INCORRECT_PORT_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                HomeActivity.showBrokerMessage(INCORRECT_QOS_MESSAGE);
                e.printStackTrace();
                return;
            }
            FirebaseTopicObj newTopicObj = new FirebaseTopicObj(topicName, Integer.valueOf(topicQoSStr));
            DatabaseReference databaseBrokerTopics = HomeActivity.getmDatabaseAuthUserBrokers().child(brokerID + "/topics");

            String topicID = UserInputConverter.convertBrokerTopicToFirebaseRules(topicName);

            databaseBrokerTopics.child(topicID).setValue(Long.valueOf(topicQoSStr), (error, ref) -> {
                if (error != null) {
                    Toast toast = Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                HomeActivity.getMqttBrokersList()
                        .get(brokerNumInList)
                        .getTopics()
                        .add(newTopicObj);
                HomeActivity.getBrokersAdapter().notifyDataSetChanged();
                dialog.dismiss();
            });
        });
    }

    private void populateDialogWidgets() {
        topicNameTv = dialogView.findViewById(R.id.mqtt_broker_new_topic);
        topicQoSTv = dialogView.findViewById(R.id.mqtt_broker_topic_qos);
    }
}