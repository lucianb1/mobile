package ro.hoptrop.core.converter;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.Base64;

/**
 * Created by Luci on 16-Feb-17.
 */
public class DayTimetableConverter {

    public static String toStringFormat() {
        return null;
    }

    public static short[][] fromString(String timetable) {
        return null;
    }

    public static void main(String[] args) {
        String text = "text";
        short[] array = new short[] {0, 1, 0, 1, 0, 1, 0, 1, 0};
        ByteBuffer byteBuffer = ByteBuffer.allocate(array.length * 2);
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        shortBuffer.put(array);

        String encoded = new String(Base64.getEncoder().encode(byteBuffer).array());
        System.out.println(encoded);
        System.out.println(Arrays.toString(Base64.getDecoder().decode(encoded)));
    }


}
