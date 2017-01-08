package ro.hoptrop.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.hoptrop.core.serializer.DayTimetableSerializer;
import ro.hoptrop.model.timetable.DayTimetable;
import ro.hoptrop.model.timetable.DayTimetableBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Luci on 08-Jan-17.
 */
public class DayTimetableRowMapper implements RowMapper<DayTimetable> {

    @Override
    public DayTimetable mapRow(ResultSet rs, int i) throws SQLException {
        return new DayTimetableBuilder()
                .setId(rs.getInt("id"))
                .setDate(rs.getDate("date"))
                .setTimetable(DayTimetableSerializer.deserialize(rs.getBytes("hours")))
                .build();
    }
}
