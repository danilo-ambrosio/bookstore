package com.sporty.bookstore.infrastructure.api.resource;

import com.google.gson.reflect.TypeToken;
import com.sporty.bookstore.infrastructure.api.resource.data.UserRegistrationData;
import com.sporty.bookstore.infrastructure.api.resource.data.UserUniqueData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserResourceTest extends ResourceTest {

    @Test
    public void testThatUserIsRegistered() throws Exception {
        final UserRegistrationData data = userRegistrationData();

        final ResultActions response =
                this.mockMvc.perform(post("/users").contentType(APPLICATION_JSON_VALUE)
                        .content(asJson(data))).andExpect(status().isCreated());

        final UserUniqueData uniqueData =
                toData(response, TypeToken.get(UserUniqueData.class));

        Assertions.assertNotNull(uniqueData.id);
        Assertions.assertEquals(data.username, uniqueData.username);
    }

    @Test
    public void testThatNonUniqueUserRegistrationFails() throws Exception {
        final UserRegistrationData data = userRegistrationData();

        this.mockMvc.perform(post("/users").contentType(APPLICATION_JSON_VALUE)
                .content(asJson(data))).andExpect(status().isCreated());

        this.mockMvc.perform(post("/users").contentType(APPLICATION_JSON_VALUE)
                .content(asJson(data))).andExpect(status().isBadRequest());
    }

    @Test
    public void testThatUserAuthenticationSucceedsWithCorrectPassword() throws Exception {
        final UserRegistrationData data = userRegistrationData();

        this.mockMvc.perform(post("/users").contentType(APPLICATION_JSON_VALUE)
                .content(asJson(data))).andExpect(status().isCreated());

        this.mockMvc.perform(get("/users?username="+data.username+"&password="+data.password))
                .andExpect(status().isOk());
    }

    @Test
    public void testThatUserAuthenticationFailsWithWrongPassword() throws Exception {
        final UserRegistrationData data = userRegistrationData();

        this.mockMvc.perform(post("/users").contentType(APPLICATION_JSON_VALUE)
                .content(asJson(data))).andExpect(status().isCreated());

        this.mockMvc.perform(get("/users?username="+data.username+"&password=wrong_pwd"))
                .andExpect(status().isUnauthorized());
    }

    private UserRegistrationData userRegistrationData() {
        return UserRegistrationData.from("eager_reader", "123456");
    }
}
