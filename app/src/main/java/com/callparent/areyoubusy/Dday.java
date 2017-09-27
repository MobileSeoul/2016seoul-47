package com.callparent.areyoubusy;

import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jihong on 2016-09-26.
 */
public class Dday {
    public int caldate(int myear, int mmonth, int mday) {
        try {
            Calendar today = Calendar.getInstance(); //현재 오늘 날짜
            Calendar dday = Calendar.getInstance();

            dday.set(myear,mmonth,mday);// D-day의 날짜를 입력합니다.

            long day = dday.getTimeInMillis()/86400000;
            // 각각 날의 시간 값을 얻어온 다음
            //( 1일의 값(86400000 = 24시간 * 60분 * 60초 * 1000(1초값) ) )


            long tday = today.getTimeInMillis()/86400000;
            long count = tday - day; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            return (int) count+1; // 날짜는 하루 + 시켜줘야합니다.
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public int caldate1(int myear, int mmonth, int mday) {
        try {
            Calendar today = Calendar.getInstance(); //현재 오늘 날짜
            Calendar dday = Calendar.getInstance();

            dday.set(2016,mmonth,mday);// D-day의 날짜를 입력합니다.

            long day = dday.getTimeInMillis()/86400000;
            // 각각 날의 시간 값을 얻어온 다음
            //( 1일의 값(86400000 = 24시간 * 60분 * 60초 * 1000(1초값) ) )


            long tday = today.getTimeInMillis()/86400000;
            long count = day - tday; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            if(count<0)
            {
                dday.set(2017,mmonth,mday);
                day = dday.getTimeInMillis()/86400000;
                count = 365-tday-day;
            }
            return (int) count+1-31; // 날짜는 하루 + 시켜줘야합니다.
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public int caldate2 (Date date){
        long result = 0;

        String end = getDate().substring(0, 16);//16개 인덱스까지 쪼개기

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date endDate = formatter.parse(end);

            Date beginDate = date;


            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴



            long diff = endDate.getTime() - beginDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            Log.d("TEST", String.valueOf(diff/60000));

            result = (diffDays);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int)result;
    }

    public String getDate(){// 현재 시간 구하는 함수
        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        Date date = new Date();
        String strDate = dateFormat.format(date);
        return strDate;
    }
}
