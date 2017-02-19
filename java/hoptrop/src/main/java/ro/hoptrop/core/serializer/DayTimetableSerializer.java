package ro.hoptrop.core.serializer;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Luci on 08-Jan-17.
 */
public class DayTimetableSerializer {

    private static final int QUARTERS = 96;

    public static byte[] serialize(short[] timetable) {
        ByteBuffer buffer = ByteBuffer.allocate(QUARTERS * 2);
        ShortBuffer shortBuffer = buffer.asShortBuffer();
        shortBuffer.put(timetable);
        return buffer.array();
    }

    public static short[] deserialize(byte[] byteArray) {
        int bytesCount = QUARTERS * 2;
        if (byteArray.length != bytesCount) {
            throw new RuntimeException("Unsupported format");
        }
        short[] result = new short[QUARTERS];
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        ShortBuffer shortBuffer = buffer.asShortBuffer();
        shortBuffer.get(result);
        return result;
}

}
