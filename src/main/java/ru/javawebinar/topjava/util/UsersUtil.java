package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(null, "user", "eUser", "passUser", Role.ROLE_USER),
            new User(null, "admin", "eAdmin", "passAdmin", Role.ROLE_ADMIN),
            new User(null, "drunkedUser", "eDrunkedUser", "eweParochky", Role.ROLE_DRUNKED_USER)
    );
}
