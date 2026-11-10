package ru.demo.oauth2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ResourceServerApiTest {
    @Autowired MockMvc mockMvc;

    @Test
    void protectedEndpointRejectsAnonymousRequest() throws Exception {
        mockMvc.perform(get("/api/user/me")).andExpect(status().isUnauthorized());
    }

    @Test
    void protectedEndpointAcceptsAuthenticatedJwt() throws Exception {
        mockMvc.perform(get("/api/user/me").with(jwt().jwt(jwt -> jwt
                        .subject("alice")
                        .claim("scope", java.util.List.of("profile", "api.read")))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value("alice"));
    }
}
