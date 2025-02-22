package com.sporty.bookstore.usecase.identity;

import com.sporty.bookstore.domain.model.user.User;
import com.sporty.bookstore.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationUseCase {

    private final UserRepository userRepository;

    public UserAuthenticationUseCase(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(final String username, final String password) {
        if (username == null || password == null) {
            throw new AuthenticationException();
        }
        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(AuthenticationException::new).toUser();
    }

}
