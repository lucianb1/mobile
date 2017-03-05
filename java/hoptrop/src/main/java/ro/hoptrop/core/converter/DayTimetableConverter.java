package ro.hoptrop.core.converter;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Base64;

import static ro.hoptrop.core.constants.TimetableConstants.*;

/**
 * Created by Luci on 16-Feb-17.
 */
public class DayTimetableConverter {

    public static byte[] fromShortAray(short[] array) {
        if (array.length != QUARTERS_IN_DAY) {
            throw new RuntimeException("Invalid timetable length: " + array.length);
        }
        ByteBuffer buffer = ByteBuffer.allocate(WEEK_TIMETABLE_BYTES_SIZE);
        ShortBuffer shortBuffer = buffer.asShortBuffer();
        shortBuffer.put(array);
        return buffer.array();
    }

    public static String toStringFormat(byte[] timetable) {
        return Base64.getEncoder().encodeToString(timetable);
    }

    public static byte[] toBytes(String timetable) {
        return Base64.getDecoder().decode(timetable);
    }

    public static String toStringFormat(short[] timetable) {
        return Base64.getEncoder().encodeToString(fromShortAray(timetable));
    }

    public static short[] fromString(String timetable) {
        return fromByteArray(toBytes(timetable));
    }

    public static short[] fromByteArray(byte[] bytes) {
        if (bytes.length != DAY_TIMETABLE_BYTES_SIZE) {
            throw new RuntimeException("Invalid timetable");
        }
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        ShortBuffer shortBuffer = buffer.asShortBuffer();

        return shortBuffer.array();
    }


}
