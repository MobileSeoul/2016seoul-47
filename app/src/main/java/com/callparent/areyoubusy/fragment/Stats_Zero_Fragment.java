package com.callparent.areyoubusy.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.callparent.areyoubusy.R;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import static com.callparent.areyoubusy.MainActivity.db;


public class Stats_Zero_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    static View view;
    TextView goalText;
    Spinner spinner;
    SeekBar satsSeekBar;
    SharedPreferences goalValue;
    SharedPreferences.Editor editor;
    ArrayAdapter<String> spinnerAdapter;
    static HashMap<String, Integer> purposeHash;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sats_0, container, false);

        purposeHash=new HashMap<>();


        //스피너 설정
        String[] names;
        if(db.countRecord()==0) {

            names=new String[1];
            names[0]="등록된 profile이 없습니다.";

        } else{

            String[] phones=db.getPhoneArray();

            names=new String[db.countRecord()];

            for (int i = 0; i < db.countRecord(); i++) {

                names[i] = db.getSelectName(phones[i]);
            }
        }

        spinner=(Spinner)view.findViewById(R.id.spinner_stats_zero);
        spinner.setOnItemSelectedListener(this);
        spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinnerAdapter.notifyDataSetChanged();
        //스피너 설정끝




        satsSeekBar=(SeekBar)view.findViewById(R.id.seekBar_stats_zero);
        goalText=(TextView)view.findViewById(R.id.sats_goal);

        goalValue=getActivity().getSharedPreferences("goalValue", MODE_PRIVATE);
        editor=goalValue.edit();

        satsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if(progress==0){
                    goalText.setText("월 10회 이상 통화");
                }else if(progress==1) {
                    goalText.setText("월 15회 이상 통화");
                }else if(progress==2){
                    goalText.setText("월 20회 이상 통화 ");
                }else{
                    goalText.setText("월 25회 이상 통화 ");
                }

                editor.putInt(spinner.getSelectedItem().toString(), progress);
                purposeHash.put(spinner.getSelectedItem().toString(),progress);
                editor.apply();
            }
        });


        return view;
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //스피너의 아이템이 선택되었을때 처리하는 메소드
        satsSeekBar.setProgress(goalValue.getInt(spinner.getSelectedItem().toString(),0));
        purposeHash.put(spinner.getSelectedItem().toString(),0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}