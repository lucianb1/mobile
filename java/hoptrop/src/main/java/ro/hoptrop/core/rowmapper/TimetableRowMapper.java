package ro.hoptrop.core.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.hoptrop.model.timetable.Timetable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Luci on 17-Dec-16.
 */
public class TimetableRowMapper implements RowMapper<Timetable> {
    ObjectInputStream inputStream;

    @Override
    public Timetable mapRow(ResultSet rs, int i) throws SQLException {
        int[] hours = null;
        try {
            inputStream = new ObjectInputStream(new ByteArrayInputStream(rs.getBytes("hours")));
            hours = (int[]) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return Timetable.builder()
                .setId(rs.getInt("id"))
                .setDate(rs.getDate("date"))
                .setMemberID(rs.getInt("member_id"))
                .setHours(hours)
                .build();
    }
}
