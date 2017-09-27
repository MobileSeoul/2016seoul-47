package com.callparent.areyoubusy;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.callparent.areyoubusy.database.DBAdapter;
import com.callparent.areyoubusy.item.ProfileIconTextItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;



public class ProfileSettingActivity extends ActionBarActivity {

    Button marryButton, birthdayButton;
    ActionBar abar;
    EditText name,phone,push;
    Button saveButton;
    String str;
    boolean isButtonClicked = true;
    DBAdapter db;
    int birth_month;
    int birth_day;
    int wedding_month;
    int wedding_day;
    Integer pushvalue;
    public static HashMap<String,Integer> pushhash=new HashMap<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilesetting);
        List<ProfileIconTextItem> menu_list = new ArrayList<ProfileIconTextItem>();

        abar=this.getSupportActionBar();
        abar.setSubtitle("프로필 설정");



        Calendar cal1= Calendar.getInstance();
        SimpleDateFormat simple1=new SimpleDateFormat("MM월 dd일");
        str=simple1.format(cal1.getTime());

        birthdayButton = (Button) findViewById(R.id.birthdayButton);
        marryButton = (Button) findViewById(R.id.marryButton);
        name=(EditText)findViewById(R.id.nameEditText);
        phone = (EditText)findViewById(R.id.phoneNumEditText);
        saveButton=(Button)findViewById(R.id.saveButton);
        push=(EditText)findViewById(R.id.pushEditText);

        phone.getText().toString();
        name.getText().toString();

        birthdayButton.setText("날짜 선택");
        marryButton.setText("날짜 선택");

    }

    public void onSaveButtonClicked(View v) {

        try {
            db = new DBAdapter(this);


            String name = this.name.getText().toString();
            String phone = this.phone.getText().toString(); //기본키값

            CallDetails callDetail = new CallDetails(this, phone);

            String birth = birthdayButton.getText().toString();
            String wedding = marryButton.getText().toString();
            int callvolume = callDetail.getCallSum(true);
            int callcount = callDetail.getCallCount(true);
            int callpassdate = callDetail.getNoCallTerm();

            pushvalue = Integer.parseInt("" + push.getText().toString());
            pushhash.put(phone,pushvalue);
            if (birthdayButton.getText().toString().equals("날짜 선택")) {
                birth = "";
            }
            if (marryButton.getText().toString().equals("날짜 선택")) {
                wedding = "";
            }

            AlarmManager mAlarmMgr;
            mAlarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            if (pushvalue == 0) {
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                // 알람 중지
                mAlarmMgr.cancel(pIntent);
            } else {
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                intent.putExtra("번호",phone);
                intent.putExtra("이름",name);
                PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                long diff = (pushvalue-callpassdate)* (24 * 60 * 60 * 1000);

                if (diff < 0) {
                    diff = 0;
                }

                mAlarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, diff,24 * 60 * 60 * 1000, pIntent);
            }


            ProfileIconTextItem d1 = new ProfileIconTextItem(name, phone, birth, wedding, callvolume, callcount, callpassdate);

            db.addContact(d1);


            Intent intent = new Intent();
            intent.putExtra("result_msg", "결과가 넘어간다");
            setResult(0, intent);

            finish();
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "올바른 프로필을 입력하시오.", Toast.LENGTH_LONG).show();

        }
    }





    //상단툴바
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_back:  // 새로고침 메뉴 선택
                Intent intent = new Intent();
                setResult(0,intent);

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void onBackPressed() {

        Intent intent = new Intent();
        setResult(0,intent);

        finish();


    }


    //생일 선택 버튼
    public void onButton1Clicked(View v) {

        DatePickerDialog dialog1 = new DatePickerDialog(this, listener, 2016, 11, 3);
        dialog1.show();
        isButtonClicked = true;
    }
    //결혼기념일 선택 버튼
    public void onButton2Clicked(View v) {

        DatePickerDialog dialog2 = new DatePickerDialog(this, listener,2016, 11, 3);
        dialog2.show();
        isButtonClicked = false;
    }


    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear++;
            if(isButtonClicked == true){
                birthdayButton.setText(monthOfYear + "월 " + dayOfMonth+"일");
                birth_month=monthOfYear;
                birth_day=dayOfMonth;
            }else{
                marryButton.setText(monthOfYear + "월 " + dayOfMonth+"일");
                wedding_month=monthOfYear;
                wedding_day=dayOfMonth;
            }
        }
    };
}
