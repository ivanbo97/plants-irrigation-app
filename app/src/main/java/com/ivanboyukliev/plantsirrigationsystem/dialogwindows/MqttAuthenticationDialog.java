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

import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.dialogwindows.api.MqttCredentialsInputListener;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.CREDENTIALS_DIALOG_TITLE;

public class MqttAuthenticationDialog extends AppCompatDialogFragment {

    private View dialogView;
    private EditText username;
    private EditText password;
    private MqttCredentialsInputListener credentialsInputListener;

    public MqttAuthenticationDialog(MqttCredentialsInputListener credentialsInputListener) {
        this.credentialsInputListener = credentialsInputListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.mqtt_broker_auth, null);
        populateDialogWidgets();
        dialogBuilder.setView(dialogView)
                .setTitle(CREDENTIALS_DIALOG_TITLE)
                .setNegativeButton("Cancel", (dialog, which) -> {
                    //no-action needed for now
                })
                .setPositiveButton("Confirm", (dialog, which) -> {
                    credentialsInputListener.onCredentialsEntered(username.getText().toString(), password.getText().toString());
                });
        return dialogBuilder.create();
    }


    private void populateDialogWidgets() {
        username = dialogView.findViewById(R.id.mqtt_username);
        password = dialogView.findViewById(R.id.mqtt_user_password);
    }
}
