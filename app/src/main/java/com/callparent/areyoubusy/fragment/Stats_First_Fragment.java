package com.callparent.areyoubusy.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.callparent.areyoubusy.CallDetails;
import com.callparent.areyoubusy.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import static com.callparent.areyoubusy.MainActivity.db;


public class Stats_First_Fragment extends Fragment {

   BarChart chart;
    View view;
    Spinner rankspinner;
    CallDetails callDetails;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sats_1, container, false);

        try{
        textView1=(TextView)view.findViewById(R.id.sats_1_1);
        textView2=(TextView)view.findViewById(R.id.sats_1_2);
        textView3=(TextView)view.findViewById(R.id.sats_1_3);
        textView4=(TextView)view.findViewById(R.id.sats_1_4);


        rankspinner=(Spinner)Stats_Zero_Fragment.view.findViewById(R.id.spinner_stats_zero);

        callDetails=new CallDetails(this.getActivity(),db.getSelectPhone(rankspinner.getSelectedItem().toString()));

        chart = (BarChart)view.findViewById(R.id.chart);

        List<BarEntry> entries = new ArrayList<>();


        BarEntry firstBar=new BarEntry(3,callDetails.getCallCount(false));
        BarEntry firstBar1=new BarEntry(4.5f,callDetails.getCallSum(false)/60);
        BarEntry secondBar=new BarEntry(7,callDetails.getCallCount(true));
        BarEntry secondBar2=new BarEntry(8.5f,callDetails.getCallSum(true)/60);
        entries.add(firstBar);
        entries.add(firstBar1);
        entries.add(secondBar);
        entries.add(secondBar2);

        textView1.setText(callDetails.getCallCount(false)+"회");
        textView2.setText(callDetails.getCallSum(false)/60+"분");
        textView3.setText(callDetails.getCallCount(true)+"회");
        textView4.setText(callDetails.getCallSum(true)/60+"분");

        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        set.setColor(Color.DKGRAY);
        set.setColors(new int[]{Color.RED,Color.RED,Color.YELLOW,Color.YELLOW});

        BarData data = new BarData(set);
        data.setValueTextSize(15);
        data.setHighlightEnabled(true);
        data.setDrawValues(true);
        data.setBarWidth(1f);
        // set custom bar width

                chart.setData(data);
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.invalidate(); // refresh
        chart.setDrawValueAboveBar(true);
        chart.animateY(2000);
        chart.setBackgroundColor(Color.LTGRAY);
        chart.setDescription("");
        chart.getXAxis().setEnabled(false);
        return view;

    } catch (Exception e) {
        Toast.makeText(getActivity(), "등록된 profile이 없습니다.", Toast.LENGTH_LONG).show();
        return view;

    }


    }



}