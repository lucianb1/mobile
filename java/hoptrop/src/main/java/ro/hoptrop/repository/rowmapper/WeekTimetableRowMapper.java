package ro.hoptrop.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.hoptrop.core.converter.WeekTimetableConverter;
import ro.hoptrop.model.timetable.WeekTimetable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Luci on 17-Dec-16.
 */
public class WeekTimetableRowMapper implements RowMapper<WeekTimetable> {

    @Override
    public WeekTimetable mapRow(ResultSet rs, int i) throws SQLException {
        byte[] timetableBytes = rs.getBytes("timetable");
        return WeekTimetable.builder()
                .setId(rs.getInt("id"))
                .setMemberID(rs.getInt("member_id"))
                .setWeekTimetable(WeekTimetableConverter.fromByteArray(timetableBytes))
                .build();
    }
}
