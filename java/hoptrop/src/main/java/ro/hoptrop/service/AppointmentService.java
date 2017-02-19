package ro.hoptrop.service;

import ro.hoptrop.web.request.appointment.NewAppointmentRequest;

/**
 * Created by Luci on 14-Jan-17.
 */
public interface AppointmentService {
    void makeAppointment(int accountID, NewAppointmentRequest request);
}
