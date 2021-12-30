package com.ardunioSerial.crux.utils;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


public class Helper1 {

    private static final String TAG="Helper";

    public static final long MAX_ENTRIES = 150;

    public void addEntry(float entry, LineChart chart, String label, String fillColor) {
        Log.d("helper", String.valueOf(entry));

        LineData data = chart.getData();
        Log.d("data", String.valueOf(data));

        if (data != null) {
            LineDataSet set = (LineDataSet) data.getDataSetByIndex(0);
            Log.d("not  null", String.valueOf(set));
            // set.addEntry(...); // can be called as well
            if (set == null) {
                set = createSet(label, fillColor);
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(), entry), 0);

            data.notifyDataChanged();



            // let the chart know it's data has changed
            chart.notifyDataSetChanged();

            // limit the number of visible entries
            // chart.setVisibleXRangeMaximum(MAX_ENTRIES);
            // chart.getAxisLeft().setAxisMaximum(entry+2);
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            if (set.getEntryCount() == MAX_ENTRIES) {
                set.removeFirst();
                // change Indexes - move to beginning by 1
                for (Entry en : set.getValues())
                    en.setX(en.getX() - 1);
            }
            // move to the latest entry
            chart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // YAxis.AxisDependency.LEFT);
        }
        else{
            chart.setData(new LineData());
        }
    }

    private static LineDataSet createSet(String label, String fillColor) {

        LineDataSet set = new LineDataSet(null, label);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.parseColor(fillColor));
        set.setCircleColor(Color.WHITE);
        set.setDrawCircles(false);
        set.setDrawCircleHole(false);
        set.setDrawFilled(true);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(Color.parseColor(fillColor));
        // set.setHighlightEnabled(false);
        set.setHighLightColor(Color.BLUE);
        set.setValueTextColor(Color.RED);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        // set.setMode(LineDataSet.Mode.);

        return set;
    }






    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
