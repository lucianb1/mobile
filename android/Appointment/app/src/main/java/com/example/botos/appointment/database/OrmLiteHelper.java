package com.example.botos.appointment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.botos.appointment.models.UserModel;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by gabibotos on 15/03/16.
 */
public class OrmLiteHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "AppointmentData.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<UserModel, Integer> mUserModelDao;

    public OrmLiteHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public OrmLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Dao<UserModel, Integer> getUserModelDao() {
        if (mUserModelDao == null) {
            try {
                mUserModelDao = getDao(UserModel.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mUserModelDao;
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
