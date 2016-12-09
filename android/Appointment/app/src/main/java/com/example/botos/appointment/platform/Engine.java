package com.example.botos.appointment.platform;


import com.example.botos.appointment.models.UserModel;

/**
 * Created by ovidiul on 2/11/15.
 */

public class Engine {
    private static Engine instance;

    public UserModel userModel;

    /**
     * Single-tone implementation
     * @return Instance of object.
     */
    public static Engine getInstance() {
        if (Engine.instance == null) {
            Engine.instance = new Engine();
        }
        return instance;
    }

    public static UserModel getCurrentUser() {
        if (Engine.getInstance().userModel == null) {
            Engine.getInstance().userModel = new UserModel();
        }
        return Engine.getInstance().userModel;
    }
}