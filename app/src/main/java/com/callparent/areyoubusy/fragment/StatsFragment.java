package com.callparent.areyoubusy.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.callparent.areyoubusy.R;

public class StatsFragment extends Fragment {


    public static final int FRAGMENT_ZERO= 0;
    public static final int FRAGMENT_ONE = 1;
    public static final int FRAGMENT_TWO = 2;
    View view;
    Fragment currentFragment;
    Button button1;
    Button button2;
    Button button0;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stats, container, false);

        button0 = (Button) view.findViewById(R.id.fragmentButton0);
        button1 = (Button) view.findViewById(R.id.fragmentButton1);
        button2 = (Button) view.findViewById(R.id.fragmentButton2);

        setOnClickListener();

        fragmentReplace(FRAGMENT_ZERO);

        return view;
    }

    public void setOnClickListener() {
            button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentReplace(FRAGMENT_ZERO);
            }
        });
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        fragmentReplace(FRAGMENT_ONE);
                    }
                });

            button2.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                        fragmentReplace(FRAGMENT_TWO);
                    }
                });
            }

    public void fragmentReplace(int index) {

                currentFragment = getFragment(index);

                final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

                transaction.replace(R.id.statsFragment_fragment,currentFragment);// 상대레이아웃에 현재 프래그먼트를 대

                transaction.commit();

            }

    private Fragment getFragment(int index) {
        Fragment newFragment = null;

        switch (index) {
            case FRAGMENT_ZERO:
                newFragment=new Stats_Zero_Fragment();
                return newFragment;
            case FRAGMENT_ONE:
                newFragment=new Stats_First_Fragment();
                return newFragment;
            case FRAGMENT_TWO:
                newFragment=new Stats_Second_Fragment();
                return newFragment;
            default:
                break;
        }
        return newFragment;
    }
}
