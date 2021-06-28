package com.ivanboyukliev.plantsirrigationsystem.navmenu.irrigationhistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanboyukliev.plantsirrigationsystem.R;

public class IrrigationHistoryFragment extends Fragment {

    private IrrigationHistoryViewModel irrigationHistoryViewModel;

    private RecyclerView irrigationRecordsRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        irrigationHistoryViewModel =
                new ViewModelProvider(this).get(IrrigationHistoryViewModel.class);

        View root = inflater.inflate(R.layout.fragment_irrigation_history, container, false);
        irrigationRecordsRecyclerView = root.findViewById(R.id.irrigationsListRecyclerView);
        irrigationRecordsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        irrigationRecordsRecyclerView.setLayoutManager(verticalLayoutManager);
        irrigationRecordsRecyclerView.setAdapter(IrrigationHistoryViewModel.getIrrigationRecordsAdapter());
        return root;
    }
}
