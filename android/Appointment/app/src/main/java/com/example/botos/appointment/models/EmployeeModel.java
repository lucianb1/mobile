package com.example.botos.appointment.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gabibotos on 22/12/16.
 */

public class EmployeeModel implements Parcelable{

    private int mId;
    private String mName;

    public EmployeeModel() {}

    protected EmployeeModel(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
    }

    public static final Creator<EmployeeModel> CREATOR = new Creator<EmployeeModel>() {
        @Override
        public EmployeeModel createFromParcel(Parcel in) {
            return new EmployeeModel(in);
        }

        @Override
        public EmployeeModel[] newArray(int size) {
            return new EmployeeModel[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
    }
}
