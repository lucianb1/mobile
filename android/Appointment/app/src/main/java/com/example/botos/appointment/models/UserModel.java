package com.example.botos.appointment.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Botos on 12/9/2016.
 */
@DatabaseTable(tableName = "Users")
public class UserModel implements Parcelable {

    public static final String NORMAL_USER = "USER";
    public static final String MEMBER = "MEMBER";
    public static final String ADMIN_MEMBER = "ADMIN_MEMBER";
    public static final String ADMIN = "ADMIN";

    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String ACCOUNTNONEXPIRED = "accountNonExpired";
    public static final String ACCOUNTNONLOCKED = "accountNonLocked";
    public static final String CREDENTIALSNONEXPIRED = "credentialsNonExpired";
    public static final String ENABLED = "enabled";
    public static final String PHONE = "phone";
    public static final String TOKEN = "token";
    public static final String IS_LOGING = "isLoging";
    public static final String USER_TYPE = "userType";

    @DatabaseField(columnName = ID, canBeNull = false, id = true)
    private int mId;
    @DatabaseField(columnName = USERNAME)
    private String mUserName;
    @DatabaseField(columnName = PASSWORD)
    private String mPassword;
    @DatabaseField(columnName = NAME)
    private String mName;
    @DatabaseField(columnName = ACCOUNTNONEXPIRED)
    private Boolean mAccountNonExpired;
    @DatabaseField(columnName = ACCOUNTNONLOCKED)
    private Boolean mAccountNonLocked;
    @DatabaseField(columnName = CREDENTIALSNONEXPIRED)
    private Boolean mCredentialsNonExpired;
    @DatabaseField(columnName = ENABLED)
    private Boolean mEnabled;
    @DatabaseField(columnName = PHONE)
    private String mPhone;
    @DatabaseField(columnName = TOKEN)
    private String mToken;
    @DatabaseField(columnName = USER_TYPE)
    private String mUserType;
    @DatabaseField(columnName = IS_LOGING)
    private boolean mIsLoging;

    public UserModel() {}

    protected UserModel(Parcel in) {
        mUserName = in.readString();
        mPassword = in.readString();
        mName = in.readString();
        mPhone = in.readString();
        mToken = in.readString();
        mUserType = in.readString();
        mAccountNonExpired = in.readByte() != 0;
        mAccountNonLocked = in.readByte() != 0;
        mCredentialsNonExpired = in.readByte() != 0;
        mEnabled = in.readByte() != 0;
        mIsLoging = in.readByte() != 0;
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
        dest.writeString(mUserType);
        dest.writeByte((byte) (mAccountNonExpired ? 1 : 0));
        dest.writeByte((byte) (mAccountNonLocked ? 1 : 0));
        dest.writeByte((byte) (mCredentialsNonExpired ? 1 : 0));
        dest.writeByte((byte) (mEnabled ? 1 : 0));
        dest.writeByte((byte) (mIsLoging ? 1 : 0));
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

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String mUserType) {
        this.mUserType = mUserType;
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

    public boolean isIsLoging() {
        return mIsLoging;
    }

    public void setIsLoging(boolean mIsLoging) {
        this.mIsLoging = mIsLoging;
    }
}