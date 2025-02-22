package com.sporty.bookstore.infrastructure.api.resource.data;

import com.sporty.bookstore.domain.model.user.User;

public class UserUniqueData {

    public final String id;
    public final String username;

    public UserUniqueData(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserUniqueData from(final User user) {
        return new UserUniqueData(user.id().value(), user.username());
    }

}
