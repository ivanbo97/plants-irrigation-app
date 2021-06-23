package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.PlantIrrigationFragment;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners.MaintainMoistureBtnListener;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.widgetslisteners.MaintainMoistureTaskInterruptListener;

public class MoistureManagementWidgets {

    private static Button submitMoistureBtn;
    private static Button terminateMoistureTaskBtn;
    private static EditText enteredMoisture;
    private Fragment parentFragment;

    public MoistureManagementWidgets(View rootView, Fragment parentFragment) {
        submitMoistureBtn = rootView.findViewById(R.id.submitMoistureBtn);
        terminateMoistureTaskBtn = rootView.findViewById(R.id.terminateMoistureTaskBtn);
        enteredMoisture = rootView.findViewById(R.id.inputMoisture);
        this.parentFragment = parentFragment;
        setWidgetsClickListeners();
    }

    private void setWidgetsClickListeners() {

        MaintainMoistureBtnListener maintainMoistureBtnListener = new MaintainMoistureBtnListener( (PlantIrrigationFragment) parentFragment);
        submitMoistureBtn.setOnClickListener(maintainMoistureBtnListener);

        MaintainMoistureTaskInterruptListener interruptMoistureTaskListener = new MaintainMoistureTaskInterruptListener((PlantIrrigationFragment) parentFragment);
        terminateMoistureTaskBtn.setOnClickListener(interruptMoistureTaskListener);
    }

    public void setWidgetsActive(boolean activeState){
        submitMoistureBtn.setEnabled(activeState);
        enteredMoisture.setEnabled(activeState);
    }

    public static EditText getEnteredMoisture() {
        return enteredMoisture;
    }

    public static Button getTerminateMoistureTaskBtn() {
        return terminateMoistureTaskBtn;
    }

    public static Button getSubmitMoistureBtn() {
        return submitMoistureBtn;
    }
}
