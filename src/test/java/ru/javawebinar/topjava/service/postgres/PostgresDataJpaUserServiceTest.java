package ru.javawebinar.topjava.service.postgres;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles({Profiles.DATAJPA, Profiles.POSTGRES})
public class PostgresDataJpaUserServiceTest extends UserServiceTest {
    //    @Test
//    User getWithMeals(int id) throws Exception {}
}
