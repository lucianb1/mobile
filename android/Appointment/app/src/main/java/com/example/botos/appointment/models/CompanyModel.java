package com.example.botos.appointment.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabibotos on 22/12/16.
 */

public class CompanyModel implements Parcelable{

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String LATI = "latitude";
    public static final String LONGI = "longitude";

    private int mId;
    private String mName;
    private String mAddress;
    private double mLati;
    private double mLongi;

    public CompanyModel() {}

    protected CompanyModel(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mAddress = in.readString();
        mLati = in.readDouble();
        mLongi = in.readDouble();
    }

    public static final Creator<CompanyModel> CREATOR = new Creator<CompanyModel>() {
        @Override
        public CompanyModel createFromParcel(Parcel in) {
            return new CompanyModel(in);
        }

        @Override
        public CompanyModel[] newArray(int size) {
            return new CompanyModel[size];
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

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public double getLati() {
        return mLati;
    }

    public void setLati(double mLati) {
        this.mLati = mLati;
    }

    public double getLongi() {
        return mLongi;
    }

    public void setLongi(double mLongi) {
        this.mLongi = mLongi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeString(mAddress);
        parcel.writeDouble(mLati);
        parcel.writeDouble(mLongi);
    }
}
