package com.callparent.areyoubusy.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.callparent.areyoubusy.AlarmReceiver;
import com.callparent.areyoubusy.GeneralDialog;
import com.callparent.areyoubusy.ProfileSettingActivity;
import com.callparent.areyoubusy.R;
import com.callparent.areyoubusy.adapter.ProfileIconTextListAdapter;
import com.callparent.areyoubusy.database.DBAdapter;
import com.callparent.areyoubusy.item.ProfileIconTextItem;

import java.util.ArrayList;
import java.util.List;

import static com.callparent.areyoubusy.MainActivity.db;


public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    ListView listView;
    ProfileIconTextListAdapter adapter;
    View view;
    TextView textView;
    Button plusButton;
    Button deleteButton;
    Button callButton;
    int size;
    GeneralDialog mGeneralDialog;
    int REQUEST_ACT = 1;
    int RESULT_OK = 0;
    List<ProfileIconTextItem> menu_list;
    ArrayAdapter<String> spinnerAdapter;
    Spinner spinner;
    String[] phones=new String[10];


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        plusButton = (Button)view.findViewById(R.id.profile_plusbutton);
        textView = (TextView)view.findViewById(R.id.profile_textView);
        listView = (ListView)view.findViewById(R.id.profile_ListView);
        deleteButton=(Button)view.findViewById(R.id.profile_deleteButton);
        callButton=(Button)view.findViewById(R.id.profile_callButton);

        //스피너 설정
        String[] phones=db.getPhoneArray();

        String[] names=new String[db.countRecord()];

        for(int i=0;i<db.countRecord();i++){

            names[i]=db.getSelectName(phones[i]);
        }


        spinner=(Spinner)view.findViewById(R.id.profile_spinner);
        spinner.setOnItemSelectedListener(this);
        spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinnerAdapter.notifyDataSetChanged();
        //스피너 설정끝

        menu_list = new ArrayList<ProfileIconTextItem>();

        menu_list=db.getAllMenu();

        adapter = new ProfileIconTextListAdapter(getActivity(),textView,plusButton,spinner,deleteButton);

        listView.setAdapter(adapter);

        size =menu_list.size();

        if(size==0) {
            callButton.setVisibility(View.GONE);
            plusButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else {
            callButton.setVisibility(View.VISIBLE);
            plusButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }

        /*db.deleteAll();*/

       for(int i=0 ; i<size; i++) {
            adapter.add(menu_list.get(i));
        }



        adapter.notifyDataSetChanged();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                Toast.makeText(getActivity(),size,Toast.LENGTH_SHORT).show();
            }
        });



        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),ProfileSettingActivity.class);
                startActivityForResult(intent,REQUEST_ACT);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGeneralDialog = new GeneralDialog(getActivity(), leftListener, rightListener);
                mGeneralDialog.show();
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:"+db.getSelectPhone(spinner.getSelectedItem().toString())));
                startActivity(intent);
                }
                catch(Exception e){
                    Toast.makeText(getActivity(),"profile 없음",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    public View.OnClickListener leftListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mGeneralDialog.dismiss();

        }
    };

    public View.OnClickListener rightListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
            String a=spinner.getSelectedItem().toString();

            adapter.removeItem(db.getSelectPhone(a));
            db.deleteRow(db.getSelectPhone(a));

            spinnerAdapter.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
            mGeneralDialog.dismiss();

                AlarmManager mAlarmMgr;
                mAlarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

                    Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                    PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);

                    // 알람 중지
                    mAlarmMgr.cancel(pIntent);


            }catch(Exception e){
                Toast.makeText(getActivity(),"profile 없음",Toast.LENGTH_LONG).show();
            }

            if(size==0) {
                callButton.setVisibility(View.GONE);
                plusButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            } else {
                callButton.setVisibility(View.VISIBLE);
                plusButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
            }

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        db=new DBAdapter(getActivity());
        if(resultCode != RESULT_OK){
            Toast.makeText(getActivity(),"결과 실패",Toast.LENGTH_LONG).show();
            return;
        }
        if(requestCode == REQUEST_ACT){
            String resultMsg = data.getStringExtra("result_msg");
            //textView.setText(resultMsg);
            adapter.notifyDataSetChanged();

            String[] phones=db.getPhoneArray();

            String[] names=new String[db.countRecord()];

            for(int i=0;i<db.countRecord();i++){

                names[i]=db.getSelectName(phones[i]);
            }


            spinner=(Spinner)view.findViewById(R.id.profile_spinner);
            spinner.setOnItemSelectedListener(this);
            spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);

            spinnerAdapter.notifyDataSetChanged();
            //스피너 설정끝

            menu_list =new ArrayList<ProfileIconTextItem>();

            menu_list=db.getAllMenu();

            adapter = new ProfileIconTextListAdapter(getActivity(),textView,plusButton,spinner,deleteButton);

            listView.setAdapter(adapter);

            size = menu_list.size();

            if(size == 0) {
                callButton.setVisibility(View.GONE);
                plusButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            } else {
                callButton.setVisibility(View.VISIBLE);
                plusButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
            }

           /* db.deleteAll();*/

           for(int i=0 ; i<size; i++) {
               adapter.add(menu_list.get(i));
            }

            adapter.notifyDataSetChanged();




        }else {

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
