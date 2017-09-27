package com.callparent.areyoubusy.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.callparent.areyoubusy.CallDetails;
import com.callparent.areyoubusy.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.callparent.areyoubusy.MainActivity.db;
import static com.callparent.areyoubusy.fragment.Stats_Zero_Fragment.purposeHash;


public class Stats_Second_Fragment extends Fragment {


    View view;
    PieChart chart;
    Spinner rankspinner;
    CallDetails callDetails;
    int purpose;
    int selectvalue;
    int result;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sats_2, container, false);

        try {
            rankspinner = (Spinner) Stats_Zero_Fragment.view.findViewById(R.id.spinner_stats_zero);

            callDetails = new CallDetails(this.getActivity(), db.getSelectPhone(rankspinner.getSelectedItem().toString()));


            if (purposeHash.get(rankspinner.getSelectedItem().toString()) == 0) {
                purpose = 10;
            } else if (purposeHash.get(rankspinner.getSelectedItem().toString()) == 1) {
                purpose = 15;
            } else if (purposeHash.get(rankspinner.getSelectedItem().toString()) == 2) {
                purpose = 20;
            } else {
                purpose = 25;
            }


            selectvalue = callDetails.getCallCount(true);

            if (selectvalue >= purpose) {
                result = 100;
            } else {
                result = selectvalue * 100 / purpose;
            }


            chart = (PieChart) view.findViewById(R.id.piechart);
            List<PieEntry> entries = new ArrayList<>();

            PieEntry entry1 = new PieEntry(result, 1);
            entries.add(entry1);
            PieEntry entry2 = new PieEntry(100 - result, 2);
            entries.add(entry2);


            PieDataSet set = new PieDataSet(entries, "BarDataSet");
            set.setColors(new int[]{Color.LTGRAY, Color.WHITE});
            set.setValueTextColor(Color.WHITE);
            set.setValueTextSize(20);
            set.setDrawValues(false);
            PieData data = new PieData(set);// set custom bar width
            chart.setData(data);
            chart.setUsePercentValues(true);
            chart.setDescription("목표달성 수치");
            chart.setDescriptionTextSize(30);
            chart.setCenterText(result + "%를 달성 하였습니다.");
            chart.invalidate(); // refresh
            chart.animateXY(2000, 2000);

            return view;

        }catch(Exception e){
                return view;
            }


    }




}