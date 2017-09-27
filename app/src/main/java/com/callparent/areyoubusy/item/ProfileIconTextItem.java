package com.callparent.areyoubusy.item;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileIconTextItem {

	private String name;
	private String phone;
	private String birth;
	private String wedding;
	private int callvolume;
	private int callcount;
	private int callpassdate;

	public ProfileIconTextItem(Parcel in) {
		readFromParcel(in);
	}

	public ProfileIconTextItem(String name,String phone, String birth, String wedding ,int callvolume,int callcount,int callpassdate) {

		this.name=name;
		this.phone=phone;
		this.birth=birth;
		this.wedding=wedding;
		this.callvolume=callvolume;
		this.callcount=callcount;
		this.callpassdate=callpassdate;
	}


	public String getName() {return name;}
	public String getPhone() {return phone;}
	public String getBirth() {return birth;}
	public String getWedding() {return wedding;}
	public int getCallVolume() {return callvolume; }
	public int getCallCount() {return callcount;}
	public int getCallPassDate() {return callpassdate;}


	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(phone);
		dest.writeString(birth);
		dest.writeString(wedding);
		dest.writeInt(callvolume);
		dest.writeInt(callcount);
		dest.writeInt(callpassdate);

	}

	public void readFromParcel(Parcel in) {
		name=in.readString();
		phone=in.readString();
		birth=in.readString();
		wedding=in.readString();
		callvolume=in.readInt();
		callcount=in.readInt();
		callpassdate=in.readInt();

	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public ProfileIconTextItem createFromParcel(Parcel in) {
			return new ProfileIconTextItem(in);
		}

		public ProfileIconTextItem[] newArray(int size) {
			return new ProfileIconTextItem[size];
		}

	};
	public int describeContents() {
		return 0;
	}

}