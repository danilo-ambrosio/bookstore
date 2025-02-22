package com.sporty.bookstore.infrastructure.api.resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sporty.bookstore.infrastructure.repository.MongoDBExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MongoDBExtension.class)
public abstract class ResourceTest {

  @Autowired
  protected Gson gson;

  @Autowired
  protected MockMvc mockMvc;

  protected <T> T toData(final ResultActions resultActions, final TypeToken<T> type) throws UnsupportedEncodingException {
    final String responseContent = resultActions.andReturn().getResponse().getContentAsString();
    return gson.fromJson(responseContent, type.getType());
  }

  protected String asJson(final Object object) {
    return gson.toJson(object);
  }

}
