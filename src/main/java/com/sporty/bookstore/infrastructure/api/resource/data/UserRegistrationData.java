package com.sporty.bookstore.infrastructure.api.resource.data;

public class UserRegistrationData {

    public final String username;
    public final String password;

    public static UserRegistrationData from(final String username, final String password) {
        return new UserRegistrationData(username, password);
    }

    private UserRegistrationData(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
