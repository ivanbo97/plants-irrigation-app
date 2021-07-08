package com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.home.HomeFragment;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata.datachart.MoistureDataChart;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata.utils.LiveDataChecker;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata.utils.TempRange;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata.utils.TempRangeDataListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DB_URL;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MAINTAIN_MOISTURE_TASK_STATE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.DELAYED_START_STATE_TOPIC;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.SEASONS;

public class RealtimeDataFragment extends Fragment {

    private RealtimeDataViewModel realtimeDataViewModel;
    private LineChart lineChart;
    private LineData moistureData;
    private MoistureDataChart moistureChart;
    private List<TempRange> tempRanges;
    private TextView currentTipsTv;
    private TextView currentTemperatureTv;
    private TextView observedPlantTv;
    private TextView currentSeasonTv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        realtimeDataViewModel =
                new ViewModelProvider(this).get(RealtimeDataViewModel.class);

        moistureData = PlantManagerActivity.getMoistureChartData();

        tempRanges = new ArrayList<>();
        View root = inflater.inflate(R.layout.fragment_realtime, container, false);

        TextView connStateTv = root.findViewById(R.id.connStatusTvRealtime);
        TextView runningTaskTv = root.findViewById(R.id.runningTaskTv);
        observedPlantTv = root.findViewById(R.id.observedPlantTv);

        currentTemperatureTv = root.findViewById(R.id.currentTempTv);
        TextView currentMoistureTv = root.findViewById(R.id.currentMoistureTv);
        currentSeasonTv = root.findViewById(R.id.currentSeasonTv);
        currentTipsTv = root.findViewById(R.id.recommendationsTv);

        lineChart = root.findViewById(R.id.moistureChart);
        moistureChart = new MoistureDataChart(lineChart);
        moistureChart.setLineData(moistureData);

        String plantApiIdAndName = HomeFragment.getPlantName();
        int separatorIdx = plantApiIdAndName.indexOf("|");
        String plantName = plantApiIdAndName.substring(0, separatorIdx);
        observedPlantTv.setText(plantName);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentMonth = calendar.get(Calendar.MONTH);

        currentSeasonTv.setText(" " + SEASONS[currentMonth]);

        LiveDataChecker liveDataChecker = new LiveDataChecker(currentTipsTv, currentSeasonTv, currentTemperatureTv, observedPlantTv, this);

        ScheduledExecutorService executor =
                Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(liveDataChecker, 2, 5, TimeUnit.SECONDS);

        realtimeDataViewModel.getBrokerConnState().observe(getViewLifecycleOwner(), connected -> {
            if (connected) {
                connStateTv.setText("Connected");
                return;
            }
            connStateTv.setText("Disconnected");
            clearLiveDataWidgetsContent();
            runningTaskTv.setText("");
        });

        realtimeDataViewModel.getMoistureValue().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String moistureValue) {
                try {
                    moistureChart.addEntry(Float.valueOf(moistureValue));
                    currentMoistureTv.setText(moistureValue + " %");
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Received a corrupted moisture value!", Toast.LENGTH_LONG);
                    e.printStackTrace();
                }
            }
        });

        realtimeDataViewModel.getTemperatureValue().observe(getViewLifecycleOwner(), temperatureValue -> {
            if (temperatureValue.equals(currentTemperatureTv.getText().toString())) {
                return;
            }
            currentTemperatureTv.setText(temperatureValue);
        });

        realtimeDataViewModel.getRunningTask().observe(getViewLifecycleOwner(), runningTask -> {
            if (runningTask.equals(MAINTAIN_MOISTURE_TASK_STATE_TOPIC) || runningTask.equals(DELAYED_START_STATE_TOPIC)) {
                runningTaskTv.setText(runningTask);
                return;
            }
            // Only pump running without any specific mode
            runningTaskTv.setText("Pumpstate");
        });

        realtimeDataViewModel.getIrrigationSystemState().observe(getViewLifecycleOwner(), irrigationSystemState -> {
            if (!irrigationSystemState.isAnyTaskRunning()) {
                runningTaskTv.setText("");
            }
        });
        return root;
    }

    private void clearLiveDataWidgetsContent() {
        RelativeLayout liveDataRelLayout = getActivity().findViewById(R.id.liveDataLayout);
        for (int i = 0; i < liveDataRelLayout.getChildCount(); i++) {
            TextView textView = (TextView) liveDataRelLayout.getChildAt(i);
            int viewId = textView.getId();
            //Prevents clearing Live Data title
            if (viewId == R.id.receivedDataFromBrokerTitle)
                continue;
            textView.setText("");
        }
    }

    public List<TempRange> getTempRanges() {
        return tempRanges;
    }

    public void onTemperatureRangesExtracted() {
        int currentTemperature = Integer.parseInt(currentTemperatureTv.getText().toString());
        for (TempRange range : tempRanges) {

            int minRange = range.getMinTemperature();
            int maxRange = range.getMaxTemperature();
            System.out.println("MinRange" + minRange);
            if (currentTemperature >= minRange && currentTemperature <= maxRange) {
                String tempRange = minRange + "-" + maxRange;

                String plantName = observedPlantTv.getText().toString();
                Log.i(plantName, "PLANTNAME");
                String currentSeason = currentSeasonTv.getText().toString().trim();
                Log.i(currentSeason, "INFO");
                FirebaseDatabase.getInstance(DB_URL)
                        .getReference("growingHints/" +
                                plantName + '/' + currentSeason + '/' +
                                "tempRange/" + tempRange).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            return;
                        }

                        String retrievedTip = task.getResult().getValue(String.class);
                        currentTipsTv.setText(retrievedTip);
                    }
                });
            }
        }
    }
}