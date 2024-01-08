package com.mintyn.codingtest;

import com.mintyn.codingtest.model.dto.AuthToken;
import com.mintyn.codingtest.model.dto.UserDto;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class CardResourceTest extends TestApplication {
    String token;

    @BeforeAll
    void registerAndLogin() throws Exception {
        var payload = new UserDto("user", "useR122@");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk()
        );

        String content = mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk()
        ).andReturn().getResponse().getContentAsString();

        var authToken = mapper.readValue(content, AuthToken.class);
        token =  authToken.token();
    }


    @Test
    void testVerifyCardSecured() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/card-scheme/123456")
        ).andExpectAll(
                MockMvcResultMatchers.status().isUnauthorized()
        );
    }

    @Test
    void testVerifyCard() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/card-scheme/123456")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk()
        ).andReturn().getResponse().getContentAsString();
    }

    @Test
    void testVerifyCardWithWrongBinLength() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/card-scheme/12346")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
        ).andExpectAll(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    void testCheckStatNoParam() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/card-scheme/stat")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
        ).andExpectAll(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    void testCheckStatInvalidParam() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/card-scheme/stat?start=-1&limit=0")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
        ).andExpectAll(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    void testCheckStat() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/card-scheme/stat?start=1&limit=1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk()
        );
    }
}
