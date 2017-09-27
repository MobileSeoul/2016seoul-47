package com.callparent.areyoubusy.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContentResolverCompat;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.callparent.areyoubusy.HashMapSort;
import com.callparent.areyoubusy.R;
import com.callparent.areyoubusy.adapter.RankIconTextListAdapter;
import com.callparent.areyoubusy.item.RankIconTextItem;

import junit.framework.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by songtong-il on 16. 8. 21..
 */
public class RankFragment extends Fragment {

    View view;
    ListView ranklistview;
    ListView ranklistview_2;
    RankIconTextListAdapter adapter;
    RankIconTextListAdapter adapter_2;
    HashMap<String, String> callName;
    HashMap<String, Integer> callVolume;
    HashMap<String, Integer> callFrequency;
    public static HashMap<String, String> callPhone;


    Cursor managedCursor;



    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rank, container, false);



        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.translate);//애니메이션

        callName=new HashMap<>();
        callVolume=new HashMap<>();
        callFrequency=new HashMap<>();
        callPhone=new HashMap<>();

        managedCursor=
                ContentResolverCompat.query(
                        this.getActivity().getContentResolver(),
                        CallLog.Calls.CONTENT_URI,
                        null,
                        null,
                        null,
                        null,
                        null);


        int name=managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        GregorianCalendar today3 = new GregorianCalendar();
        int year = today3.get(today3.YEAR);
        int month = today3.get(today3.MONTH) + 1;
        today3.add(today3.MONTH, -1);

        int beforeMonth = today3.get(today3.MONTH) + 1;
        SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM");

        while (managedCursor.moveToNext()) {

            int callDuration = managedCursor.getInt(duration);
            String phName=managedCursor.getString(name);

            if(callDuration!=0&&phName!=null) {//통화량이 0이면 무시한다.

                String phNumber=managedCursor.getString(number);
                String callDate=managedCursor.getString(date);

                Date callDayTime = new Date(Long.valueOf(callDate));

                //while 문을 멈추는 문장//

                if(callDayTime.getMonth()+1==beforeMonth) {

                    break;
                }
                //while 문을 멈추는 문장

                if(callName.get(phNumber)!=null) {
                    int volume =callVolume.get(phNumber)+callDuration;
                    int result=volume;
                    callVolume.put(phNumber,result);

                    int frequency=callFrequency.get(phNumber)+1;
                    callFrequency.put(phNumber,frequency);
                }else{
                    callName.put(phNumber,phName);
                    callPhone.put(phName,phNumber);
                    callVolume.put(phNumber,callDuration);
                    callFrequency.put(phNumber,1);

                }
            }
        }
        managedCursor.close();


        Iterator it=HashMapSort.sortByValue(callVolume).iterator();//정렬해주는 기능
        Iterator it2=HashMapSort.sortByValue(callFrequency).iterator();


        adapter = new RankIconTextListAdapter(getActivity());

        while(it.hasNext()){
            if(adapter.getCount()==20)break; // 10개 까지만 랭크 표시

            String temp = (String) it.next();
            adapter.add(new RankIconTextItem((adapter.getCount()+1)+"위",callName.get(temp),callVolume.get(temp)/60+"분"));
        }
        ranklistview=(ListView)view.findViewById(R.id.rank1_ListView);
        ranklistview.setAdapter(adapter);

        ranklistview.setAnimation(animation);




        adapter_2=new RankIconTextListAdapter(getActivity());
        while(it2.hasNext()){
            if(adapter_2.getCount()==20)break; // 10개 까지만 랭크 표시

            String temp = (String) it2.next();
            adapter_2.add(new RankIconTextItem((adapter_2.getCount()+1)+"위",callName.get(temp),callFrequency.get(temp)+"회"));
        }
        ranklistview_2=(ListView)view.findViewById(R.id.rank2_ListView);
        ranklistview_2.setAnimation(animation);

        ranklistview_2.setAdapter(adapter_2);



        return view;
    }


}
