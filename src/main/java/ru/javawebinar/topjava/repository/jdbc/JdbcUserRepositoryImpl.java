package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    @Transactional
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            insertBatch(newKey.intValue(), new ArrayList<>(user.getRoles()));
            user.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id = ?", user.getId());
            insertBatch(user.getId(), new ArrayList<>(user.getRoles()));
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT u.*, string_agg(r.role, ',') as roles FROM users u JOIN user_roles r ON u.id = r.user_id GROUP BY id ORDER BY name,email", ROW_MAPPER);
        return users;
    }
        /*List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON users.id=user_roles.user_id", new ResultSetExtractor<List<User>>() {
            @Override
            public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
                Map<Integer, User> map = new HashMap<Integer, User>();
                User user = null;
                while (rs.next()) {
                    Integer id = rs.getInt("id");
                    user = map.get(id);
                    if(user == null){
                        String email = rs.getString("email");
                        String name = rs.getString("name");
                        String password = rs.getString("password");
                        Integer calories = rs.getInt("calories_per_day");
                        Boolean enabled = rs.getBoolean("enabled");
                        user = new User(id, name, email, password, calories, enabled, null);
                        map.put(id, user);
                    }
                    Set<Role> roles = new TreeSet<Role>();
                    roles.add(Role.valueOf(rs.getString("role")));
                    user.setRoles(roles);
                }
                return new ArrayList<User>(map.values());
            }
        });
        return users;
    }*/


    public void insertBatch(int userId, List<Role> roles) {

        String sql = "INSERT INTO user_roles VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Role role = roles.get(i);
                ps.setInt(1, userId);
                ps.setString(2, role.toString());
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }

    private User setRoles(User user){
        List<Role> roles = jdbcTemplate.query("SELECT role FROM user_roles WHERE user_id = ?", (rs, rowNum) -> Role.valueOf(rs.getString("role")), user.getId());
        user.setRoles(roles);
        return user;
    }
}

