package ru.javawebinar.topjava.service.hsqldb;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles({Profiles.HSQLDB, Profiles.JPA})
public class HsqlJpaUserServiceTest extends UserServiceTest {
}
