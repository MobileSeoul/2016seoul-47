package com.callparent.areyoubusy.fragment;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.callparent.areyoubusy.MainActivity;
import com.callparent.areyoubusy.MakingActivity;
import com.callparent.areyoubusy.NoticeActivity;
import com.callparent.areyoubusy.R;

public class SettingFragment extends Fragment {
    static View view;
    LinearLayout making;
    LinearLayout notice;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        making = (LinearLayout) view.findViewById(R.id.makingLinearLayout);
        notice = (LinearLayout) view.findViewById(R.id.noticeLinearLayout);

        making.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MakingActivity.class);
                startActivity(intent);
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

}