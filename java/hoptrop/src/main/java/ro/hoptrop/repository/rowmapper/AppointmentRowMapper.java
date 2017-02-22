package ro.hoptrop.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.hoptrop.model.appointment.Appointment;
import ro.hoptrop.model.appointment.AppointmentStatus;
import ro.hoptrop.model.timetable.TimeIntervalSerializer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Luci on 17-Dec-16.
 */
public class AppointmentRowMapper implements RowMapper<Appointment> {

    @Override
    public Appointment mapRow(ResultSet rs, int i) throws SQLException {
        return Appointment.builder()
                .setId(rs.getInt("id"))
                .setMemberID(rs.getInt("member_id"))
                .setAccountID(rs.getInt("account_id"))
                .setDate(rs.getDate("date"))
                .setStatus(AppointmentStatus.valueOf(rs.getString("status")))
                .setTimeIntervals(TimeIntervalSerializer.deserialize(rs.getBytes("intervals")))
                .build();
    }
}
