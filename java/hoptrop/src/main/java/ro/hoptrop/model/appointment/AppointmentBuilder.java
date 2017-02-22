package ro.hoptrop.model.appointment;

import ro.hoptrop.model.timetable.TimeInterval;

import java.util.Date;

/**
 * Created by Luci on 17-Dec-16.
 */
public class AppointmentBuilder {

    private int id;
    private int memberID;
    private int accountID;
    private Date date;
    private TimeInterval[] timeIntervals;
    private AppointmentStatus status;

    AppointmentBuilder() {
    }

    public Appointment build() {
        return new Appointment(id, memberID, accountID, date, timeIntervals, status);
    }

    public AppointmentBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public AppointmentBuilder setMemberID(int memberID) {
        this.memberID = memberID;
        return this;
    }

    public AppointmentBuilder setAccountID(int accountID) {
        this.accountID = accountID;
        return this;
    }

    public AppointmentBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public AppointmentBuilder setTimeIntervals(TimeInterval[] timeIntervals) {
        this.timeIntervals = timeIntervals;
        return this;
    }

    public AppointmentBuilder setStatus(AppointmentStatus status) {
        this.status = status;
        return this;
    }
}
