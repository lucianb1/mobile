package com.example.botos.appointment.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Botos on 12/9/2016.
 */
public class UserModel implements Parcelable {

    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String NAME = "name";
    public static String ACCOUNTNONEXPIRED = "accountNonExpired";
    public static String ACCOUNTNONLOCKED = "accountNonLocked";
    public static String CREDENTIALSNONEXPIRED = "credentialsNonExpired";
    public static String ENABLED = "enabled";
    public static String PHONE = "phone";
    public static String TOKEN = "token";

    private String mUserName;
    private String mPassword;
    private String mName;
    private Boolean mAccountNonExpired;
    private Boolean mAccountNonLocked;
    private Boolean mCredentialsNonExpired;
    private Boolean mEnabled;
    private String mPhone;
    private String mToken;

    public UserModel() {}

    protected UserModel(Parcel in) {
        mUserName = in.readString();
        mPassword = in.readString();
        mName = in.readString();
        mPhone = in.readString();
        mToken = in.readString();
        mAccountNonExpired = in.readByte() != 0;
        mAccountNonLocked = in.readByte() != 0;
        mCredentialsNonExpired = in.readByte() != 0;
        mEnabled = in.readByte() != 0;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUserName);
        dest.writeString(mPassword);
        dest.writeString(mName);
        dest.writeString(mPhone);
        dest.writeString(mToken);
        dest.writeByte((byte) (mAccountNonExpired ? 1 : 0));
        dest.writeByte((byte) (mAccountNonLocked ? 1 : 0));
        dest.writeByte((byte) (mCredentialsNonExpired ? 1 : 0));
        dest.writeByte((byte) (mEnabled ? 1 : 0));
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Boolean getAccountNonExpired() {
        return mAccountNonExpired;
    }

    public void setAccountNonExpired(Boolean mAccountNonExpired) {
        this.mAccountNonExpired = mAccountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return mAccountNonLocked;
    }

    public void setAccountNonLocked(Boolean mAccountNonLocked) {
        this.mAccountNonLocked = mAccountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return mCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean mCredentialsNonExpired) {
        this.mCredentialsNonExpired = mCredentialsNonExpired;
    }

    public Boolean getEnabled() {
        return mEnabled;
    }

    public void setEnabled(Boolean mEnabled) {
        this.mEnabled = mEnabled;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }
}