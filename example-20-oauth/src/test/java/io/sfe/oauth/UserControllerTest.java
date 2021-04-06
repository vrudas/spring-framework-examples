package io.sfe.oauth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void currentUser() throws Exception {
        var principal = buildPrincipal();

        mockMvc.perform(
            get("/users/me")
            .sessionAttr(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new SecurityContextImpl(principal)
            )
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("user"));
    }

    private OAuth2AuthenticationToken buildPrincipal() {
        var attributes = Map.<String, Object>of("display_name", "user");
        var authorities = Set.of(new OAuth2UserAuthority(attributes));
        var oauth2User = new DefaultOAuth2User(authorities, attributes, "display_name");

        return new OAuth2AuthenticationToken(
            oauth2User,
            authorities,
            "test_client_registration_id"
        );
    }
}
