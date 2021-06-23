package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.dialogpickers.DatePickerFragment;
import com.ivanboyukliev.plantsirrigationsystem.dialogpickers.TimePickerFragment;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners.SubmitDelayedStartBtnListener;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners.TerminateDelayedStartBtnListener;

public class DelayedPumpStartWidgets {

    private Button setDateBtn;
    private Button setTimeBtn;
    private static Button terminateDelayedStartBtn;
    private static Button submitDelayedStartBtn;

    private static EditText enteredIrrigationDuration;
    private static TextView inputDateTv;
    private static TextView inputTimeTv;

    private FragmentActivity parentFragment;

    public DelayedPumpStartWidgets(View rootView, FragmentActivity parentFragment) {
        setDateBtn = rootView.findViewById(R.id.pickUpDateBtn);
        setTimeBtn = rootView.findViewById(R.id.pickUpTimeBtn);
        submitDelayedStartBtn = rootView.findViewById(R.id.submitDelayStart);
        terminateDelayedStartBtn = rootView.findViewById(R.id.terminateDelayedStartBtn);
        enteredIrrigationDuration = rootView.findViewById(R.id.inputIrrigationDuration);
        inputDateTv = rootView.findViewById(R.id.chosenDateTv);
        inputTimeTv = rootView.findViewById(R.id.chosenTimeTv);
        this.parentFragment = parentFragment;
        setWidgetsListeners();
    }

    private void setWidgetsListeners() {

        setDateBtn.setOnClickListener(v -> {
            DialogFragment dateDialog = new DatePickerFragment(R.layout.date_picker_spinner);
            dateDialog.show(parentFragment.getSupportFragmentManager(), "DatePicker");
        });

        setTimeBtn.setOnClickListener(v -> {
            DialogFragment timeDialog = new TimePickerFragment();
            timeDialog.show(parentFragment.getSupportFragmentManager(), "TimePicker");
        });

        SubmitDelayedStartBtnListener delayStartBtnListener = new SubmitDelayedStartBtnListener(parentFragment);

        submitDelayedStartBtn.setOnClickListener(delayStartBtnListener);

        TerminateDelayedStartBtnListener terminateDelStartListener = new TerminateDelayedStartBtnListener(parentFragment);
        terminateDelayedStartBtn.setOnClickListener(terminateDelStartListener);
    }

    public void setWidgetsActive(boolean activeState) {
        setDateBtn.setEnabled(activeState);
        setTimeBtn.setEnabled(activeState);
        submitDelayedStartBtn.setEnabled(activeState);
        enteredIrrigationDuration.setEnabled(activeState);
    }

    public static Button getTerminateDelayedStartBtn() {
        return terminateDelayedStartBtn;
    }

    public static Button getSubmitDelayedStartBtn() {
        return submitDelayedStartBtn;
    }

    public static TextView getInputDateTv() {
        return inputDateTv;
    }

    public static TextView getInputTimeTv() {
        return inputTimeTv;
    }

    public static EditText getEnteredIrrigationDuration() {
        return enteredIrrigationDuration;
    }
}
