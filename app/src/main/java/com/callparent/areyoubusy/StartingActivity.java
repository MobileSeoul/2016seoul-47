package com.callparent.areyoubusy;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class StartingActivity extends Activity {


    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_starting);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(StartingActivity.this,WordingActivity.class);
                startActivity(i);
                finish();

                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        }, 2000);


    }

}
