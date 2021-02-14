package io.sfe.userflow.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(
        username = "user",
        password = "pswd",
        roles = "USER"
    )
    void current_user_was_returned() throws Exception {
        mockMvc.perform(get("/users/me"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isMap())
            .andExpect(jsonPath("$.username").value("user"))
            .andExpect(jsonPath("$.password").value("pswd"))
            .andExpect(jsonPath("$.authorities").isArray())
            .andExpect(jsonPath("$.authorities[0]").isMap())
            .andExpect(jsonPath("$.authorities[0].authority").value("ROLE_USER"))
        ;
    }
}
