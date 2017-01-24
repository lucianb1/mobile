package com.example.botos.appointment.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Botos on 1/14/2017.
 */

public class ServicesModel implements Parcelable{

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DURATION = "duration";

    private int mId;
    private String mName;
    private int mDuration;

    public ServicesModel() {}

    protected ServicesModel(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mDuration = in.readInt();
    }

    public static final Creator<ServicesModel> CREATOR = new Creator<ServicesModel>() {
        @Override
        public ServicesModel createFromParcel(Parcel in) {
            return new ServicesModel(in);
        }

        @Override
        public ServicesModel[] newArray(int size) {
            return new ServicesModel[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mDuration);
    }
}
