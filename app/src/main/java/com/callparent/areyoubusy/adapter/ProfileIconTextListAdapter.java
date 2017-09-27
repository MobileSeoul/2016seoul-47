package com.callparent.areyoubusy.adapter;

import android.util.Log;
import android.view.View;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.callparent.areyoubusy.CallDetails;
import com.callparent.areyoubusy.GeneralDialog;
import com.callparent.areyoubusy.R;
import com.callparent.areyoubusy.database.DBAdapter;
import com.callparent.areyoubusy.item.ProfileIconTextItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.callparent.areyoubusy.MainActivity.db;

public class ProfileIconTextListAdapter extends BaseAdapter {


    private Spinner spinner;
    private ProfileIconTextItem profileItem;
    private LayoutInflater inflater;
    private int isClicked = 0;
    private ListView listView;
    static ProfileIconTextListAdapter myAdapter;
    TextView textView, birthdayDday, marryDday;
    Button plusButton;
    Button deleteButton;
    int j = 1;

    private View.OnClickListener mOnClickListener = null;
    private ArrayList<Integer> intList;


    ViewHolder holder;
    Context context;
    int number;


    private GeneralDialog mCustomDialog;

    public ArrayList<ProfileIconTextItem> List_Data;

    public ProfileIconTextListAdapter(Context context, TextView textView, Button plusButton, Spinner spinner, Button deleteButton) {
        super();
        this.spinner = spinner;
        this.plusButton = plusButton;
        this.deleteButton = deleteButton;
        this.textView = textView;
        inflater = LayoutInflater.from(context);
        List_Data = new ArrayList<ProfileIconTextItem>();
        this.myAdapter = this;
    }

    @Override
    public int getCount() {
        return List_Data.size();
    }

    @Override
    public ProfileIconTextItem getItem(int position) {
        return List_Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        context = parent.getContext();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.profile_listitem, null);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.proifile_itemImage);
            holder.name = (TextView) convertView.findViewById(R.id.profile_name);
            holder.phone = (TextView) convertView.findViewById(R.id.profile_phone);
            holder.birth = (TextView) convertView.findViewById(R.id.profile_birth);
            holder.wedding = (TextView) convertView.findViewById(R.id.profile_wedding);
            holder.callvolume = (TextView) convertView.findViewById(R.id.profile_callvolume);
            holder.callcount = (TextView) convertView.findViewById(R.id.profile_callcount);
            holder.callpassdate = (TextView) convertView.findViewById(R.id.profile_callpassdate);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        profileItem = getItem(position);
        number = position;

        if (profileItem != null) {
            holder.name.setText(profileItem.getName());
            holder.phone.setText("번호  " + profileItem.getPhone());
            holder.birth.setText("생일  " + profileItem.getBirth());
            holder.wedding.setText("결혼기념일  " + profileItem.getWedding());
            holder.callvolume.setText("통화량  " + profileItem.getCallVolume() / 60 + "분");//인트로 set하면 오류남
            holder.callcount.setText("통화횟수  " + profileItem.getCallCount() + "회");

            if (profileItem.getCallPassDate() == 999) {
                holder.callpassdate.setText("연락안한지 두 달이상 경과했습니다.");
            } else {
                holder.callpassdate.setText("연락안한지 " + profileItem.getCallPassDate() + "일째 입니다.");
            }

        }
        return convertView;
        }


    public void removeItem(String phone){

        int index=0;

        if(List_Data.get(0).getPhone().equals(phone)) {

            List_Data.remove(0);

        }else {
            Iterator iterator = List_Data.iterator();

            while (iterator.hasNext()) {
                ProfileIconTextItem element = (ProfileIconTextItem) iterator.next();
                if (element.getPhone().equals(phone)) {
                    index++;
                    break;
                }

            }
            List_Data.remove(index);
        }
    }


	public void add(ProfileIconTextItem profile) { List_Data.add(profile);}
	public void removeAll() {List_Data.clear()


	//db도 날려야함 기능 추가
	;}

	private class ViewHolder {
		public ImageView imageView;
		public TextView name;
		public TextView phone;
		public TextView birth;
		public TextView wedding;
		public TextView callvolume;
		public TextView callcount;
        public TextView callpassdate;
	}
}
