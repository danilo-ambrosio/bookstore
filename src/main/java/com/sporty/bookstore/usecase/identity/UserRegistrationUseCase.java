package com.sporty.bookstore.usecase.identity;

import com.sporty.bookstore.domain.model.user.User;
import com.sporty.bookstore.infrastructure.repository.UserData;
import com.sporty.bookstore.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationUseCase {

    private final UserRepository userRepository;

    public UserRegistrationUseCase(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(final String username, final String password) {
        if(userRepository.existsByUsername(username)) {
            throw new UniqueUsernameException();
        }
        final User user = User.with(username, password);
        userRepository.save(UserData.from(user));
        return user;
    }

}
