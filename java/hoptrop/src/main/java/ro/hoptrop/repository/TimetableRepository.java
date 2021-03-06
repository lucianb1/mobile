package ro.hoptrop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.model.timetable.DayTimetable;
import ro.hoptrop.model.timetable.WeekTimetable;
import ro.hoptrop.repository.rowmapper.DayTimetableRowMapper;
import ro.hoptrop.repository.rowmapper.WeekTimetableRowMapper;

import java.util.Date;

/**
 * Created by Luci on 18-Dec-16.
 */
@Repository
public class TimetableRepository {

    private static final WeekTimetableRowMapper weekTimetableRowMapper = new WeekTimetableRowMapper();
    private static final DayTimetableRowMapper dayTimetableRowMapper = new DayTimetableRowMapper();

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public WeekTimetable getMemberDefaultTimetable(int memberID) {
        String sql = "SELECT * FROM member_default_timetables where member_id = :memberID";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberID", memberID);
        try {
            return jdbcTemplate.queryForObject(sql, params, weekTimetableRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    public DayTimetable getMemberDayTimetable(int memberID, Date date) {
        String sql = "SELECT * FROM day_timetables WHERE member_id = :memberID AND date = :date";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberID", memberID)
                .addValue("date", date);
        try {
            return jdbcTemplate.queryForObject(sql, params, dayTimetableRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    public void createDefaultTimetable(int memberID) {
        String sql = "INSERT INTO member_default_timetables (member_id, timetable) values (:memberID, :timetable)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberID", memberID)
                .addValue("timetable", 0); //TODO
        jdbcTemplate.update(sql ,params);
    }

    public void createDayTimetable(int memberID, byte[] timetable) {
        String sql = "INSERT INTO member_day_timetables (member_id, date, timetable) values (:memberID, CURDATE(), :timetable)";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("memberID", memberID)
            .addValue("timetable", timetable);
        jdbcTemplate.update(sql ,params);
    }

    public void updateDayTimetable(int memberID, Date date, byte[] timetable) {
        String sql = "UPDATE member_day_timetables SET timetable = :timetable WHERE member_id = :memberID AND date = :date";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("memberID", memberID)
            .addValue("date", date)
            .addValue("timetable", timetable);
        jdbcTemplate.update(sql ,params);
    }

    public void updateDefaultTimetable(int memberID, byte[] timetable) {
        String sql = "UPDATE member_default_timetables SET timetable = :timetable where member_id = :memberID";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberID", memberID)
                .addValue("timetable", timetable);
        jdbcTemplate.update(sql, params);
    }

    public void deleteDefaultTimetableForMember(int memmberID) {
        String sql = "DELETE FROM member_default_timetables WHERE member_id = :memberID";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberID", memmberID);
        jdbcTemplate.update(sql, params);
    }

}
