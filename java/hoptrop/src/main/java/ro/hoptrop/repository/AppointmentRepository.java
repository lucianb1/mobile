package ro.hoptrop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.model.appointment.Appointment;
import ro.hoptrop.model.appointment.AppointmentStatus;
import ro.hoptrop.repository.rowmapper.AppointmentRowMapper;

import java.util.Date;
import java.util.List;

/**
 * Created by Luci on 22-Feb-17.
 */
@Repository
public class AppointmentRepository {

    private static final AppointmentRowMapper rowMapper = new AppointmentRowMapper();

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Appointment findAppointment(int id) {
        String sql = "SELECT * FROM appointments WHEREid = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        try {
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    public void createAppointment(int memberID, int userID, Date date, int hour, int serviceID) {
        String sql = "INSERT INTO appointments (member_id, user_id, date, hour, serviceID, status) " +
            "VALUES (:memberID, :userID, :date, :hour, :serviceID, :status)";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("memberID", memberID)
            .addValue("userID", userID)
            .addValue("date", date)
            .addValue("hour", hour)
            .addValue("status", AppointmentStatus.ACTIVE.name())
            .addValue("serviceID", serviceID);
        jdbcTemplate.update(sql, params);
    }

    public void deleteAppointments(Date date) {
        String sql = "DELETE FROM appointments WHERE date = :date";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("date", date);
        jdbcTemplate.update(sql, params);
    }

    public List<Appointment> getActiveAppointmentsForMember(int memberID, Date date) {
        String sql = "SELECT * FROM appointments " +
            "WHERE member_id = :memberID " +
            "AND date = :date " +
            "AND status = :status " +
            "ORDER BY date ASC";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("memberID", memberID)
            .addValue("status", AppointmentStatus.ACTIVE.name())
            .addValue("date", date);
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    public void updateAppointmentStatus(int id, AppointmentStatus newStatus) {
        String sql = "UPDATE appointments SET status = :status WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("status", newStatus.name());
        jdbcTemplate.update(sql, params);
    }



}
