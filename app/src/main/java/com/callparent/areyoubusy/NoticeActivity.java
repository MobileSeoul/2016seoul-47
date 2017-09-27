package com.callparent.areyoubusy;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class NoticeActivity extends ActionBarActivity {

    ActionBar abar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        abar=this.getSupportActionBar();
        abar.setSubtitle("공지사항");

        }

//상단 툴바
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_back:  // 새로고침 메뉴 선택
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }



}
