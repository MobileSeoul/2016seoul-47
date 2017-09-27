package com.callparent.areyoubusy.item;

import android.os.Parcel;
import android.os.Parcelable;

public class RankIconTextItem {

	private String number;
	private String name;
	private String data;

	public RankIconTextItem(Parcel in) {
		readFromParcel(in);
	}

	public RankIconTextItem(String number, String name, String data) {

		this.number=number;
		this.data=data;
		this.name=name;

	}
	public String getNumber() {return number;}
	public String getName() {return name;}
	public String getData() {return data;}


	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(number);
		dest.writeString(name);
		dest.writeString(data);
	}

	public void readFromParcel(Parcel in) {
		number=in.readString();
		name=in.readString();
		data=in.readString();
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public RankIconTextItem createFromParcel(Parcel in) {
			return new RankIconTextItem(in);
		}

		public RankIconTextItem[] newArray(int size) {
			return new RankIconTextItem[size];
		}

	};
	public int describeContents() {
		return 0;
	}


}