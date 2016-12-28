package com.example.botos.appointment.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabibotos on 22/12/16.
 */

public class DomainModel implements Parcelable{

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String OREDER_NR = "orderNr";

    private int mId;
    private String mName;
    private int mOrderNr;

    public DomainModel() {}

    protected DomainModel(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mOrderNr = in.readInt();
    }

    public static final Creator<DomainModel> CREATOR = new Creator<DomainModel>() {
        @Override
        public DomainModel createFromParcel(Parcel in) {
            return new DomainModel(in);
        }

        @Override
        public DomainModel[] newArray(int size) {
            return new DomainModel[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getOrderNr() {
        return mOrderNr;
    }

    public void setOrderNr(int mOrderNr) {
        this.mOrderNr = mOrderNr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeInt(mOrderNr);
    }
}
