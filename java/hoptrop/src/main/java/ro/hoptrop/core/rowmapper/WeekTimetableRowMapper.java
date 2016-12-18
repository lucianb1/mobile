package ro.hoptrop.core.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.hoptrop.model.timetable.WeekTimetable;
import ro.hoptrop.model.timetable.WeekTimetableSerializer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Luci on 17-Dec-16.
 */
public class WeekTimetableRowMapper implements RowMapper<WeekTimetable> {

    @Override
    public WeekTimetable mapRow(ResultSet rs, int i) throws SQLException {
        return WeekTimetable.builder()
                .setId(rs.getInt("id"))
                .setMemberID(rs.getInt("member_id"))
                .setWeekTimetable(WeekTimetableSerializer.deserialize(rs.getBytes("timetable")))
                .build();
    }
}
