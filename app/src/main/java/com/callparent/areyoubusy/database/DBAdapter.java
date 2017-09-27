package com.callparent.areyoubusy.database;

/**
 * Created by tong-ilsong on 2016. 10. 27..
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.callparent.areyoubusy.item.ProfileIconTextItem;

import java.util.ArrayList;
import java.util.List;

import static com.callparent.areyoubusy.MainActivity.db;

public class DBAdapter {


    private static final String DATABASE_NAME = "AreyoubusyReal";
    private static final String DATABASE_TABLE = "PhoneRecord";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;




    //생성테이블
    private static final String DATABASE_CREATE="CREATE TABLE " +DATABASE_TABLE+ "" +
            " (    NAME TEXT NOT NULL,   PHONE TEXT PRIMARY    KEY,    BIRTH TEXT,    WEDDING TEXT,    " +
            "CALLVOLUME INTEGER,     CALLCOUNT INTEGER,     CALLPASSDATE INTEGER)";

    public void addContact(ProfileIconTextItem m) {
        Toast.makeText(mCtx,"삽입",Toast.LENGTH_LONG).show();
        db.insert(m.getName(),m.getPhone(),m.getBirth(),m.getWedding(),m.getCallVolume(),m.getCallCount(),m.getCallPassDate());
        db.close();
    }


    public static class DatabaseHelper extends SQLiteOpenHelper {


        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //데이터베이스 최초 생성될때 실행 디비가 생성될때 실행된다
            Log.d("TEST","onCreate DATABSE_CREATE");
            db.execSQL(DATABASE_CREATE);

        }

        /**
         *
         * @param db         The database.
         * @param oldVersion The old database version.
         * @param newVersion The new database version.
         */
        @Override
        //데이터베이스가 업그레이드가 필요할때
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // db.execSQL( SQL_DELETE_TABLE);
        }
    }

    //
    public void open() throws SQLException {

        mDbHelper = new DatabaseHelper(mCtx);
/*
        DB가 없다면 onCreate가 호출 후 생성, version이 바뀌었다면 onUpgrade 메소드 호출
        mDb = mDbHelper.getWritableDatabase();
*/
        // 권한부여 읽고 쓰기를 위해
        mDb = mDbHelper.getWritableDatabase();
    }

    public DBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    //닫기
    public void close() {
        mDbHelper.close();
    }


    //데이터베이스에 삽입
    public long insert(String name,String phone, String birth, String wedding ,int callvolume,int callcount,int callpassdate) {
        db.open();
        ContentValues insertValues = new ContentValues();

        insertValues.put("NAME", name);
        insertValues.put("PHONE", phone);
        insertValues.put("BIRTH", birth);
        insertValues.put("WEDDING", wedding);
        insertValues.put("CALLVOLUME",callvolume);
        insertValues.put("CALLCOUNT",callcount);
        insertValues.put("CALLPASSDATE",callpassdate);

        return mDb.insert(DATABASE_TABLE, null, insertValues);
    }
    //업데이트~
    public long update(String name,String phone, String birth, String wedding ,int callvolume,int callcount,int callpassdate) {
        db.open();
        ContentValues updateValues = new ContentValues();
        updateValues.put("NAME", name);
        updateValues.put("PHONE", phone);
        updateValues.put("BIRTH", birth);
        updateValues.put("WEDDING", wedding);
        updateValues.put("CALLVOLUME", callvolume);
        updateValues.put("CALLCOUNT", callcount);
        updateValues.put("CALLPASSDATE", callpassdate);
        return mDb.update(DATABASE_TABLE, updateValues, "PHONE" + "=?", new String[]{phone});
    }

    //한개씩삭제
    public boolean deleteRow(String phone) {
        db.open();
        return mDb.delete(DATABASE_TABLE, "PHONE" + "=?", new String[]{phone}) > 0;

    }

    //다삭제
    public boolean deleteAll() {

        return mDb.delete(DATABASE_TABLE, null, null) > 0;
    }

    public Cursor AllRows() {
        return mDb.query(DATABASE_TABLE, null, null, null, null, null, null);

    }


    public List<ProfileIconTextItem> getAllMenu() {
        List<ProfileIconTextItem> Menu_List = new ArrayList<ProfileIconTextItem>();

        String selectQuery = "SELECT * FROM "+DATABASE_TABLE;

        mDbHelper=new DatabaseHelper(mCtx);
        SQLiteDatabase mdb =this.mDbHelper.getWritableDatabase();
        Cursor cursor = mdb.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()) {
            do {
                ProfileIconTextItem new_menu= new ProfileIconTextItem(
                        cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6));
                Menu_List.add(new_menu);
            } while(cursor.moveToNext());

        }

        db.close();
        return Menu_List;

    }
    //파일 소스 지정
    public int countRecord(){


        String selectQuery="SELECT * FROM "+DATABASE_TABLE;
        mDbHelper=new DatabaseHelper(mCtx);
        SQLiteDatabase mdb=this.mDbHelper.getWritableDatabase();
        Cursor cursor =mdb.rawQuery(selectQuery,null);

        int result=cursor.getCount();

        db.close();

        return result;
    }


    public String[] getPhoneArray(){
        String[] a=new String[db.countRecord()];

        int i=0;

        String selectQuery = "SELECT * FROM "+DATABASE_TABLE;

        mDbHelper=new DatabaseHelper(mCtx);
        SQLiteDatabase mdb =this.mDbHelper.getWritableDatabase();
        Cursor cursor = mdb.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()) {
            do{
                a[i]=cursor.getString(1);

                i++;
            } while(cursor.moveToNext());
        }
        db.close();
        return a;
    }


    public String getSelectName(String phone){
        String selectname = null;
        String selectQuery = "SELECT NAME,PHONE FROM "+DATABASE_TABLE;

        mDbHelper=new DatabaseHelper(mCtx);
        SQLiteDatabase mdb =this.mDbHelper.getWritableDatabase();
        Cursor cursor = mdb.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()) {
            do {
                if(cursor.getString(1).equals(phone)){
                    selectname=cursor.getString(0);
                    break;
                }
                selectname=null;
            } while(cursor.moveToNext());

        }
        db.close();
        return selectname;
    }

    public String getSelectPhone(String Name){
        String selectname = null;
        String selectQuery = "SELECT NAME,PHONE FROM "+DATABASE_TABLE;

        mDbHelper=new DatabaseHelper(mCtx);
        SQLiteDatabase mdb =this.mDbHelper.getWritableDatabase();
        Cursor cursor = mdb.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()) {
            do {
                if(cursor.getString(0).equals(Name)){
                    selectname=cursor.getString(1);
                    break;
                }
                selectname=null;
            } while(cursor.moveToNext());

        }
        db.close();
        return selectname;
    }

   public String getSelectBirth(String phone){
        String select = null;
        String selectQuery = "SELECT PHONE, BIRTH FROM "+DATABASE_TABLE;

        mDbHelper=new DatabaseHelper(mCtx);
        SQLiteDatabase mdb =this.mDbHelper.getWritableDatabase();
        Cursor cursor = mdb.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()) {
            do {
                if(cursor.getString(0).equals(phone)){
                    select=cursor.getString(1);
                    break;
                }
                select=null;
            } while(cursor.moveToNext());

        }
        db.close();
        return select;
    }

    public String getSelectWedding(String phone){
        String select = null;
        String selectQuery = "SELECT PHONE,WEDDING FROM "+DATABASE_TABLE;

        mDbHelper=new DatabaseHelper(mCtx);
        SQLiteDatabase mdb =this.mDbHelper.getWritableDatabase();
        Cursor cursor = mdb.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()) {
            do {
                if(cursor.getString(0).equals(phone)){
                    select=cursor.getString(1);
                    break;
                }
                select=null;
            } while(cursor.moveToNext());

        }
        db.close();
        return select;
    }

    public int getSelectVolume(String phone){
        int select = 0;
        String selectQuery = "SELECT PHONE,CALLVOLUME FROM "+DATABASE_TABLE;

        mDbHelper=new DatabaseHelper(mCtx);
        SQLiteDatabase mdb =this.mDbHelper.getWritableDatabase();
        Cursor cursor = mdb.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()) {
            do {
                if(cursor.getString(0).equals(phone)){
                    select=cursor.getInt(1);
                    break;
                }
                select=0;
            } while(cursor.moveToNext());

        }
        db.close();
        return select;
    }

    public int getSelectCount(String phone){
        int select = 0;
        String selectQuery = "SELECT PHONE,CALLCOUNT FROM "+DATABASE_TABLE;

        mDbHelper=new DatabaseHelper(mCtx);
        SQLiteDatabase mdb =this.mDbHelper.getWritableDatabase();
        Cursor cursor = mdb.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()) {
            do {
                if(cursor.getString(0).equals(phone)){
                    select=cursor.getInt(1);
                    break;
                }
                select=0;
            } while(cursor.moveToNext());

        }
        db.close();
        return select;
    }
}