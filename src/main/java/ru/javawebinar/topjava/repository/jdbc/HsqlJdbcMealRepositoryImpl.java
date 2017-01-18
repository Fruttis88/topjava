package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Repository
@Profile(Profiles.HSQLDB)
public class HsqlJdbcMealRepositoryImpl extends JdbcMealRepositoryImpl {

    public HsqlJdbcMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public <T> T convertLocalDateTime(LocalDateTime localDateTime) {
        return (T)Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
