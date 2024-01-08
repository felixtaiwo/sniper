package com.mintyn.codingtest;

import com.mintyn.codingtest.model.dto.AuthToken;
import com.mintyn.codingtest.model.dto.UserDto;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class UserResourceTest extends TestApplication {

    @BeforeAll
    void testRegisterUser() throws Exception {
        var payload = new UserDto("newuser", "useME@111");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    void testRegisterUserBadPassword() throws Exception {
        var payload = new UserDto("newuser", "use1");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
        ).andExpectAll(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }
    @Test
    void testRegisterAlreadyExistingUser() throws Exception {
        var payload = new UserDto("newuser", "useME@111");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
        ).andExpectAll(
                MockMvcResultMatchers.status().isConflict()
        );
    }

    @Test
    void testLogin () throws Exception{
        var payload = new UserDto("newuser", "useME@111");
        String content = mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk()
        ).andReturn().getResponse().getContentAsString();

        var authToken = mapper.readValue(content, AuthToken.class);

        Assertions.assertNotNull(authToken);
        Assertions.assertNotNull(authToken.token());
        Assertions.assertTrue(authToken.expiry()>System.currentTimeMillis());
    }

    @Test
    void testLoginWithNonExistentUsername () throws Exception{
        var payload = new UserDto("user1", "useME@111");
         mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
        ).andExpectAll(
                MockMvcResultMatchers.status().isUnauthorized()
        );
    }

    @Test
    void testLoginWithInCorrectPassword () throws Exception{
        var payload = new UserDto("user", "usME@111");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
        ).andExpectAll(
                MockMvcResultMatchers.status().isUnauthorized()
        );
    }
}
