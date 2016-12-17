package ro.hoptrop.model.appointment;

import ro.hoptrop.model.timetable.TimeInterval;

import java.util.Date;

/**
 * Created by Luci on 17-Dec-16.
 */
public class Appointment {

    private final int id;
    private final int memberID;
    private final int accountID;
    private final Date date;
    private final TimeInterval[] timeIntervals;

    public Appointment(int id, int memberID, int accountID, Date date, TimeInterval[] timeIntervals) {
        this.id = id;
        this.memberID = memberID;
        this.accountID = accountID;
        this.date = date;
        this.timeIntervals = timeIntervals;
    }

    public static AppointmentBuilder builder() {
        return new AppointmentBuilder();
    }

    public int getId() {
        return id;
    }

    public int getMemberID() {
        return memberID;
    }

    public int getAccountID() {
        return accountID;
    }

    public Date getDate() {
        return date;
    }

    public TimeInterval[] getTimeIntervals() {
        return timeIntervals;
    }
}
