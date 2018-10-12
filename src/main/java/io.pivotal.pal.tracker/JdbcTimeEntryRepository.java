package io.pivotal.pal.tracker;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository  {
    private JdbcTemplate jdbcTemplate = null;

    public JdbcTimeEntryRepository(MysqlDataSource mysqlDataSource) {
        this.jdbcTemplate = new JdbcTemplate(mysqlDataSource);
    }

    public TimeEntry create(TimeEntry entry) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(
                                "insert into time_entries (project_id, user_id, date, hours) values (?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS
                        );
                ps.setLong(1, entry.getProjectId());
                ps.setLong(2, entry.getUserId());
                ps.setDate(3, java.sql.Date.valueOf(entry.getDate()));
                ps.setLong(4, entry.getHours());
                return ps;
            },
            keyHolder
        );
        entry.setId(keyHolder.getKey().longValue());
        return entry;
    }

    public TimeEntry find(long id) {
        return jdbcTemplate.query(
                "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?",
                new Object[]{id},
                rs -> rs.next() ?
                        mapper.mapRow(rs,1)
                        : null
        );
    }

    private final RowMapper<TimeEntry> mapper = (rs,rowNum) -> new TimeEntry (
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    public List<TimeEntry> list() {
        return jdbcTemplate.query(
                "SELECT id, project_id, user_id, date, hours FROM time_entries",
                (rs, rowNum) -> new TimeEntry(
                        rs.getLong("id"),
                        rs.getLong("project_id"),
                        rs.getLong("user_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getInt("hours")
                )
        );
    }

    public TimeEntry update(long id, TimeEntry entry){
        entry.setId(id);
        jdbcTemplate.update(
                "UPDATE time_entries SET project_id = ?, user_id = ?, date = ?, hours = ? where id = ?",
                entry.getProjectId(),
                entry.getUserId(),
                entry.getDate(),
                entry.getHours(),
                entry.getId()
        );
        return entry;
    }

    public void delete(long id) {
        jdbcTemplate.update(
                "DELETE from time_entries where id = ?",
                id
        );
    }
}


