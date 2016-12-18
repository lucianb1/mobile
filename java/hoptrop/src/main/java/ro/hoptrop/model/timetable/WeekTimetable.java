package ro.hoptrop.model.timetable;

import java.time.DayOfWeek;

/**
 * Created by Luci on 17-Dec-16.
 * Every index of hours array, represents 15 mins
 */
public class WeekTimetable {

    private final int id;
    private final int memberID;
    private final short[][] weekTimtable;

    WeekTimetable(int id, int memberID, short[][] weekTimtable) {
        this.id = id;
        this.memberID = memberID;
        this.weekTimtable = weekTimtable;
    }

    public static WeekTimetableBuilder builder() {
        return new WeekTimetableBuilder();
    }

    public int getId() {
        return id;
    }

    public int getMemberID() {
        return memberID;
    }


    public short[][] getWeekTimetable() {
        return weekTimtable;
    }

    public short[] getDayTimetable(int day) {
        return weekTimtable[day];
    }

    public short[] getDayTimetable(DayOfWeek dayOfWeek) {
        return weekTimtable[dayOfWeek.getValue()];
    }

    public boolean hasTimetableSet() {
        return weekTimtable != null;
    }
}
