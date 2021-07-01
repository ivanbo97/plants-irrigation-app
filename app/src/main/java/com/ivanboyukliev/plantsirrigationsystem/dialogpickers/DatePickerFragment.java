package com.ivanboyukliev.plantsirrigationsystem.dialogpickers;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.DelayedPumpStartWidgets;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    private int style;
    private DatePicker datePickerView;
    private DelayedPumpStartWidgets delayedStartWidgets;

    public DatePickerFragment(int xml, DelayedPumpStartWidgets delayedStartWidgets) {
        style = xml;
        this.delayedStartWidgets = delayedStartWidgets;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(style, null);

        Calendar calendar = Calendar.getInstance();
        datePickerView = view.findViewById(R.id.datePickerSpinner);
        datePickerView.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);

        datePickerView.setMinDate(System.currentTimeMillis() - 1000);
        return new AlertDialog.Builder(requireActivity())
                .setView(view)
                .setPositiveButton("OK", this)
                .setNegativeButton("Cancel", this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                datePickerView.clearFocus();
                onDateSet(datePickerView,
                        datePickerView.getYear(),
                        datePickerView.getMonth(),
                        datePickerView.getDayOfMonth());
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.dismiss();
                break;
        }
    }


    public void onDateSet(DatePicker view, int year, int month, int day) {
        /*Months start with index 0, so actual
          value for month needs to be incremented*/
        month += 1;
        StringBuilder givenMonth = new StringBuilder(String.valueOf(month));

        Log.i("DatePicker", "received date from picker: " + day + "." + month + "." + year);
        if (month <= 9) {
            givenMonth.insert(0, '0');
        }

        StringBuilder givenDay = new StringBuilder(String.valueOf(day));
        if(day <=9){
            givenDay.insert(0,'0');
        }
        delayedStartWidgets.getInputDateTv().setText(givenDay + "-" + givenMonth + "-" + year);
    }
}