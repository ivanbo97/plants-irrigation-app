package com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata.datachart;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class MoistureDataChart implements OnChartValueSelectedListener {

    private LineChart lineChart;

    //Data object that encapsulates all data associated with a LineChart.
    private LineData lineData;

    /*Class representing the legend of the chart. The legend will contain one entry
    per color and DataSet.*/
    private Legend chartLegend;

    private XAxis xAxis;
    private YAxis yAxisLeft;
    private YAxis yAxisRight;

    public MoistureDataChart(LineChart lineChart) {
        this.lineChart = lineChart;
        moistureChartInit();
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }


    private void moistureChartInit() {
        lineChart.setData(lineData);
        lineChart.getDescription().setText("Soil Moisture Level (%)");
        lineChart.getDescription().setTextSize(10);
        chartLegend = lineChart.getLegend();
        xAxis = lineChart.getXAxis();
        yAxisLeft = lineChart.getAxisLeft();
        yAxisRight = lineChart.getAxisRight();
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.setNoDataText("Waiting for moisture data....");
        lineChart.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(true);

        //Background color of chart
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setBorderColor(Color.rgb(67, 164, 34));

        modifyLegend();
        modifyXAxis();
        modifyYAxis();
    }

    private void modifyLegend() {
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        chartLegend.setForm(Legend.LegendForm.LINE);
        chartLegend.setTypeface(Typeface.MONOSPACE);
        chartLegend.setTextColor(Color.rgb(67, 164, 34));
    }

    private void modifyXAxis() {
        xAxis.setTypeface(Typeface.MONOSPACE);
        xAxis.setTextColor(Color.rgb(67, 164, 34));
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setEnabled(true);
    }

    private void modifyYAxis() {

        yAxisLeft.setTypeface(Typeface.MONOSPACE);
        yAxisLeft.setTextColor(Color.rgb(67, 164, 34));

        yAxisLeft.setDrawGridLines(true);

        // disable right Axis
        yAxisRight.setEnabled(false);
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.rgb(67, 164, 34));
        //set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        //set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(Color.rgb(67, 164, 34));
        set.setHighLightColor(Color.rgb(67, 164, 34));
        set.setValueTextColor(Color.rgb(67, 164, 34));
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    public void addEntry(float value) {
        LineData data = lineChart.getData();
        if (data == null) {
            return;
        }

        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well
        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }

        data.addEntry(new Entry(set.getEntryCount(), value), 0);
        Log.w("chart", set.getEntryForIndex(set.getEntryCount() - 1).toString());

        data.notifyDataChanged();

        // let the chart know it's data has changed
        lineChart.notifyDataSetChanged();

        // limit the number of visible entries
        lineChart.setVisibleXRangeMaximum(10);
        // mChart.setVisibleYRange(30, AxisDependency.LEFT);

        // move to the latest entry
        lineChart.moveViewTo(set.getEntryCount() - 1, data.getYMax(), YAxis.AxisDependency.LEFT);

        // this automatically refreshes the chart (calls invalidate())
        // mChart.moveViewTo(data.getXValCount()-7, 55f,
        // AxisDependency.LEFT);

    }

    public void setLineData(LineData lineData) {
        this.lineData = lineData;
        moistureChartInit();
    }
}
