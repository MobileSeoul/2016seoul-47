package com.callparent.areyoubusy;

import android.app.Activity;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.content.ContentResolverCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class CallDetails {

    Cursor managedCursor;
    int beforeMonthDuration;
    int currentMonthDuration;
    int beforeMonthCount;
    int currentMonthCount;

    Activity activity;

    String number; //핸드폰 번호


    public CallDetails(Activity activity,String num) {
        this.number=num;
        this.activity=activity;
        beforeMonthDuration=0;
        currentMonthDuration=0;
        beforeMonthCount=0;
        currentMonthCount=0;


        managedCursor=
                ContentResolverCompat.query(
                        activity.getContentResolver(),
                        CallLog.Calls.CONTENT_URI,
                        null,
                        CallLog.Calls.NUMBER + " IN (?, ?)",
                        new String[]{num},
                        null,
                        null);


            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);


            //현재 년 달 알기 위한
            GregorianCalendar today3 = new GregorianCalendar();
            int year = today3.get(today3.YEAR);
            int month = today3.get(today3.MONTH) + 1;
            today3.add(today3.MONTH, -1);
            int beforeMonth = today3.get(today3.MONTH) + 1;
            today3.add(today3.MONTH, -1);

            int before2month=today3.get(today3.MONTH) + 1;
            SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM");
            //


        while (managedCursor.moveToNext()) {

            int callDuration1 = managedCursor.getInt(duration);

            if(callDuration1!=0) {//통화량이 0이면 무시한다.

                StringBuffer sb = new StringBuffer();
                String phNumber = managedCursor.getString(number);
                String callDate = managedCursor.getString(date);

                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString(duration);

                //while 문을 멈추는 문장//
                if((callDayTime.getMonth()+1)==before2month) {
                    break;
                }
                //while 문을 멈추는 문장

                String a, b;

                if (month < 10) {
                    a = String.valueOf(year) + "-0" + String.valueOf(month);
                } else {
                    a = String.valueOf(year) + "-" + String.valueOf(month);
                }
                if (beforeMonth < 10) {
                    b = String.valueOf(year) + "-0" + String.valueOf(beforeMonth);
                } else {
                    b = String.valueOf(year) + "-" + String.valueOf(beforeMonth);
                }

                String k = formatter4.format(callDayTime).toString();


                //통화량과 통화횟수 더해주기
                if (k.equals(a)) {
                        currentMonthDuration = callDuration1 + currentMonthDuration;
                        currentMonthCount++;
                }

                if (k.equals(b)) {
                        beforeMonthDuration = callDuration1 + beforeMonthDuration;
                        beforeMonthCount++;
                }

                /*sb.append("\nPhone Number:--- " + phNumber +
                        "\nCall Date:--- " + callDayTime +
                        "\n통화한 달:--- " + formatter4.format(callDayTime) +
                        "\n저번 달:--- " + beforeMonth +
                        "\n통화량 :--- " + callDuration +
                        "\n10월통화량 :--- " + currentMonthDuration +
                        "\n9월통화량 :--- " + beforeMonthDuration
                );
                sb.append("\n----------------------------------");

                Log.e("kkk", sb.toString());*/
            }
        }
        managedCursor.close();


    }


    //이번달 전달 통화량 받는 메소드
    public Integer getCallSum(boolean value) {// getSumCall("01043316657",true) 이번달 통화량 출력

        Integer current=currentMonthDuration;
        Integer before=beforeMonthDuration;


        if (value == true)
            return current;
        else {
            return before;
        }
    }

    //이번달 전달 통화횟수 받는 메소드
    public Integer getCallCount(boolean value) {

        if (value == true)
            return currentMonthCount;
        else {
            return beforeMonthCount;
        }


    }
    //전화안한지 몇일인지 값을 반환 하는 메소드
    public Integer getNoCallTerm() {

        Dday dday = new Dday();

        Cursor managedCursor1=
                ContentResolverCompat.query(
                        activity.getContentResolver(),
                        CallLog.Calls.CONTENT_URI,
                        null,
                        CallLog.Calls.NUMBER + " IN (?, ?)",
                        new String[]{number},
                        null,
                        null);

        int date = managedCursor1.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor1.getColumnIndex(CallLog.Calls.DURATION);

        Date latelyDate = null;
        try {
            while (managedCursor1.moveToNext()) {

                int callDuration1 = managedCursor1.getInt(duration);

                if (callDuration1 != 0) {//통화량이 0이면 무시한다.

                    String latelyCallDate = managedCursor1.getString(date);
                    latelyDate = new Date(Long.valueOf(latelyCallDate));
                    break;
                }
            }


            return dday.caldate2(latelyDate);
        }catch (Exception e){
            return 999;
        }
    }


}