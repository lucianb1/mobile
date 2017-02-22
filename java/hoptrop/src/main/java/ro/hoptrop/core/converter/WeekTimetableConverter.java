package ro.hoptrop.core.converter;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Base64;

import static ro.hoptrop.core.constants.TimetableConstants.*;

/**
 * Created by Luci on 16-Feb-17.
 */
public class WeekTimetableConverter {

    public static String toStringFormat(byte[] timetable) {
        return Base64.getEncoder().encodeToString(timetable);
    }

    public static byte[] toBytes(String timetable) {
        return Base64.getDecoder().decode(timetable);
    }

    public static byte[] fromShortAray(short[][] timetable) {
        ByteBuffer buffer = ByteBuffer.allocate(WEEK_TIMETABLE_BYTES_SIZE);
        ShortBuffer shortBuffer = buffer.asShortBuffer();
        for (int i = 0; i <timetable.length; i++) {
            shortBuffer.put(timetable[i]);
        }
        return buffer.array();
    }

    public static String toStringFormat(short[][] timetable) {
        return Base64.getEncoder().encodeToString(fromShortAray(timetable));
    }

    public static short[][] fromString(String timetable) {
        return fromByteArray(toBytes(timetable));
    }

    public static short[][] fromByteArray(byte[] bytes) {
        if (bytes.length != WEEK_TIMETABLE_BYTES_SIZE) {
            throw new RuntimeException("Invalid timetable");
        }
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        ShortBuffer shortBuffer = buffer.asShortBuffer();

        short[][] result = new short[DAYS_IN_WEEK][QUARTERS_IN_DAY];
        for (int i = 0; i < DAYS_IN_WEEK; i++) {

            int startIndex = i * QUARTERS_IN_DAY;
            int length = QUARTERS_IN_DAY;
            shortBuffer.get(result[i], startIndex, length);
        }
        return result;
    }

}
