package ru.javawebinar.topjava.service.postgres;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles({Profiles.POSTGRES, Profiles.JDBC})
public class PostgresJDBCUserServiceTest extends UserServiceTest {
}
