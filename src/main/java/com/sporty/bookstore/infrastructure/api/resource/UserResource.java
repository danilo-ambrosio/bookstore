package com.sporty.bookstore.infrastructure.api.resource;

import com.sporty.bookstore.domain.model.user.User;
import com.sporty.bookstore.infrastructure.api.resource.data.UserUniqueData;
import com.sporty.bookstore.infrastructure.api.resource.data.UserRegistrationData;
import com.sporty.bookstore.usecase.identity.UserAuthenticationUseCase;
import com.sporty.bookstore.usecase.identity.UserRegistrationUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserAuthenticationUseCase authenticationUseCase;
    private final UserRegistrationUseCase registrationUseCase;

    public UserResource(final UserAuthenticationUseCase authenticationUseCase,
                        final UserRegistrationUseCase registrationUseCase) {
        this.authenticationUseCase = authenticationUseCase;
        this.registrationUseCase = registrationUseCase;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserUniqueData> register(@RequestBody final UserRegistrationData registration) {
        final User user = registrationUseCase.register(registration.username, registration.password);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserUniqueData.from(user));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserUniqueData> authenticate(@RequestParam("username") final String username,
                                                       @RequestParam("password") final String password) {
        final User user = authenticationUseCase.authenticate(username, password);
        return ResponseEntity.ok(UserUniqueData.from(user));
    }

}
