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
    private  Button terminateDelayedStartBtn;
    private  Button submitDelayedStartBtn;

    private  EditText enteredIrrigationDuration;
    private  TextView inputDateTv;
    private  TextView inputTimeTv;

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
            DialogFragment dateDialog = new DatePickerFragment(R.layout.date_picker_spinner,this);
            dateDialog.show(parentFragment.getSupportFragmentManager(), "DatePicker");
        });

        setTimeBtn.setOnClickListener(v -> {
            DialogFragment timeDialog = new TimePickerFragment(this);
            timeDialog.show(parentFragment.getSupportFragmentManager(), "TimePicker");
        });

        SubmitDelayedStartBtnListener delayStartBtnListener = new SubmitDelayedStartBtnListener(parentFragment,this);

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

    public  Button getTerminateDelayedStartBtn() {
        return terminateDelayedStartBtn;
    }

    public  Button getSubmitDelayedStartBtn() {
        return submitDelayedStartBtn;
    }

    public  TextView getInputDateTv() {
        return inputDateTv;
    }

    public  TextView getInputTimeTv() {
        return inputTimeTv;
    }

    public  EditText getEnteredIrrigationDuration() {
        return enteredIrrigationDuration;
    }
}
