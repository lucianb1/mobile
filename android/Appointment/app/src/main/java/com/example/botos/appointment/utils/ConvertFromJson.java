package com.example.botos.appointment.utils;

import com.example.botos.appointment.models.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Botos on 12/7/2016.
 */
public class ConvertFromJson {

    public static UserModel toUserModel(JSONObject jsonObject) {
        UserModel userModel = new UserModel();

        try {
            userModel.setName(jsonObject.getJSONObject("user").getString(UserModel.NAME));
            userModel.setAccountNonExpired(jsonObject.getJSONObject("user").getBoolean(UserModel.ACCOUNTNONEXPIRED));
            userModel.setAccountNonLocked(jsonObject.getJSONObject("user").getBoolean(UserModel.ACCOUNTNONLOCKED));
            userModel.setCredentialsNonExpired(jsonObject.getJSONObject("user").getBoolean(UserModel.CREDENTIALSNONEXPIRED));
            userModel.setEnabled(jsonObject.getJSONObject("user").getBoolean(UserModel.ENABLED));
            userModel.setPassword(jsonObject.getJSONObject("user").getString(UserModel.PASSWORD));
            userModel.setPhone(jsonObject.getJSONObject("user").getString(UserModel.PHONE));
            userModel.setUserName(jsonObject.getJSONObject("user").getString(UserModel.USERNAME));
            userModel.setUserType(jsonObject.getJSONObject("user").getJSONArray("authorities").getJSONObject(0).getString("authority"));
            userModel.setToken(jsonObject.getString(UserModel.TOKEN));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userModel;
    }
}
