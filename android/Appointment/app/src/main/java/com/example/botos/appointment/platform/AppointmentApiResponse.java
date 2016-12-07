package com.example.botos.appointment.platform;

/**
 * Created by Botos on 12/7/2016.
 */
public interface AppointmentApiResponse<T> {

    //called when a request succeeded.
    void onSuccess(T response);

    //called when a request failed.
    void onFailure();

    void onFailure(String error);

}
