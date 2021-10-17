package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> users = Arrays.asList(
            new User(1, "User1", "user1@email.ru", "pass1", Role.USER),
            new User(2, "UAdmin1", "admin1@email.ru", "passAdmin1", Role.ADMIN)
    );
}
