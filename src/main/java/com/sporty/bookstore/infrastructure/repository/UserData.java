package com.sporty.bookstore.infrastructure.repository;

import com.sporty.bookstore.domain.model.user.User;
import com.sporty.bookstore.domain.model.user.UserId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class UserData {

    @Id
    public final String id;
    public final String username;
    public final String password;

    @PersistenceCreator
    public UserData(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static UserData from(User user) {
        return new UserData(user.id().value(), user.username(), user.password());
    }

    public User toUser() {
        return User.with(UserId.of(id), username, password);
    }
}
