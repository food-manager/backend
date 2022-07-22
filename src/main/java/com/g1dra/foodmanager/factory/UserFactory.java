package com.g1dra.foodmanager.factory;

import com.g1dra.foodmanager.models.User;
import com.g1dra.foodmanager.models.UserRole;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {
    private static final Faker faker = new Faker();

    public static List<User> create(int count, UserRole role) {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(createUser(role));
        }

        return list;
    }

    private static User createUser(UserRole role) {
        String name = faker.name().name();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        switch (role) {
            case REGULAR:
                return User.createUserRegular(
                        name,
                        email,
                        password
                );
            default:
                return User.createUserAdmin(
                        name,
                        email,
                        password
                );

        }
    }
}
