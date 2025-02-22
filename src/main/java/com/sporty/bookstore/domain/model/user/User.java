package com.sporty.bookstore.domain.model.user;

/**
 * This class contains user's credential for the purpose of identity and access management.
 *
 * @author Danilo Ambrosio
 */
public class User {

  private final UserId id;
  private final String username;
  private final String password;

  public static User with(final String username, final String password) {
    return with(UserId.create(), username, password);
  }

  public static User with(final UserId userId, final String username, final String password) {
    return new User(userId, username, password);
  }

  private User(final UserId id,
               final String username,
               final String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public UserId id() {
    return id;
  }

  public String username() {
    return username;
  }

  public String password() {
    return password;
  }

}
