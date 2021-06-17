package com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ivanboyukliev.plantsirrigationsystem.R;

public class RealtimeDataFragment extends Fragment {

    private RealtimeDataViewModel realtimeDataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        realtimeDataViewModel =
                new ViewModelProvider(this).get(RealtimeDataViewModel.class);
        View root = inflater.inflate(R.layout.fragment_realtime, container, false);
        final TextView textView = root.findViewById(R.id.text_realtime);
        realtimeDataViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}