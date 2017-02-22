package ro.hoptrop.core.converter;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import static ro.hoptrop.core.constants.TimetableConstants.QUARTERS_IN_DAY;
import static ro.hoptrop.core.constants.TimetableConstants.WEEK_TIMETABLE_BYTES_SIZE;

/**
 * Created by Luci on 16-Feb-17.
 */
public class DayTimetableConverter {

    public static String toStringFormat() {
        return null;
    }

    public static short[] fromString(String timetable) {
        return null;
    }

    public static byte[] fromShortAray(short[] array) {
        if (array.length != QUARTERS_IN_DAY) {
            throw new RuntimeException("Invalid timetable length: " + array.length);
        }
        ByteBuffer buffer = ByteBuffer.allocate(WEEK_TIMETABLE_BYTES_SIZE);
        ShortBuffer shortBuffer = buffer.asShortBuffer();
        shortBuffer.put(array);
        return buffer.array();
    }


}
