package com.callparent.areyoubusy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.callparent.areyoubusy.MainActivity;
import com.callparent.areyoubusy.R;
import com.callparent.areyoubusy.fragment.RankFragment;
import com.callparent.areyoubusy.item.RankIconTextItem;

import java.util.ArrayList;

import static com.callparent.areyoubusy.MainActivity.db;
import static com.callparent.areyoubusy.fragment.RankFragment.callPhone;

public class RankIconTextListAdapter extends BaseAdapter {


	private RankIconTextItem rankItem;
	private LayoutInflater inflater;
	static RankIconTextListAdapter myAdapter;
	ViewHolder holder;
	int number;
	LinearLayout rankLayout;



	private ArrayList<RankIconTextItem> List_Data;

	public RankIconTextListAdapter(Context context) {
		super();

		inflater = LayoutInflater.from(context);
		List_Data = new ArrayList<RankIconTextItem>();
		this.myAdapter = this;


	}

	@Override
	public int getCount() {
		return List_Data.size();
	}

	@Override
	public RankIconTextItem getItem(int position) {
		return List_Data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {


		convertView = inflater.inflate(R.layout.rank_listitem,null);


		LinearLayout ranklayout=(LinearLayout)convertView.findViewById(R.id.rankitemLayout);

		holder = new ViewHolder();
		holder.number=(TextView) convertView.findViewById(R.id.rank_num_textView);
		holder.name=(TextView)convertView.findViewById(R.id.rank_name_textView);
		holder.data=(TextView)convertView.findViewById(R.id.rank_data_textView);
		convertView.setTag(holder);

		rankItem = getItem(position);
		number = position;

		holder.number.setText(rankItem.getNumber());
        if(rankItem.getNumber().equals("1위")){
            holder.number.setBackgroundResource(R.drawable.rank_1rank);
            holder.number.setText("");;
        }


        holder.name.setText(rankItem.getName());
        if(db.getSelectName(callPhone.get(rankItem.getName()))!=null){
            ranklayout.setBackgroundColor(Color.GRAY);
            holder.name.setTextColor(Color.WHITE);
            holder.name.setTypeface(Typeface.DEFAULT_BOLD);;
        }
		holder.data.setText(rankItem.getData());



		return convertView;
	}



    public void add(RankIconTextItem rank) { List_Data.add(rank);}
	public void removeAll() {List_Data.clear();

	}

	private class ViewHolder {
		public TextView number;
		public TextView name;
		public TextView data;
	}


}
