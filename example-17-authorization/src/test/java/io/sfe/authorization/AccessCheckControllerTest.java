package io.sfe.authorization;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO: 15.03.2023 Fix the tests as a part of https://github.com/vrudas/spring-framework-examples/issues/101
//  implementation
@Disabled("Because of https://github.com/vrudas/spring-framework-examples/issues/101")
@WebMvcTest(AccessCheckController.class)
class AccessCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void login_page_is_available_for_user() throws Exception {
        mockMvc.perform(get("/login"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void login_page_is_available_for_admin() throws Exception {
        mockMvc.perform(get("/login"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void admin_endpoint_is_not_available_for_user() throws Exception {
        mockMvc.perform(get("/admin/system-info"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void admin_endpoint_is_available_for_admin() throws Exception {
        mockMvc.perform(get("/admin/system-info"))
            .andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    void user_name_endpoint_is_available_for_user() throws Exception {
        mockMvc.perform(get("/name"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void user_name_endpoint_is_available_for_admin() throws Exception {
        mockMvc.perform(get("/name"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void read_user_endpoint_is_not_available_for_user_without_authority_but_with_role_USER() throws Exception {
        mockMvc.perform(get("/read-user"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "READ_USERS")
    void read_user_endpoint_is_available_for_user_with_authority() throws Exception {
        mockMvc.perform(get("/read-user"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void read_user_endpoint_is_not_available_for_admin_without_authority() throws Exception {
        mockMvc.perform(get("/read-user"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN", "READ_USERS"})
    void read_user_endpoint_is_available_for_admin_with_authority() throws Exception {
        mockMvc.perform(get("/read-user"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void delete_user_endpoint_is_not_available_for_user() throws Exception {
        mockMvc.perform(get("/read-user"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void delete_user_endpoint_is_not_available_for_admin_without_authority() throws Exception {
        mockMvc.perform(get("/delete-user"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN", "DELETE_USERS"})
    void delete_user_endpoint_is_available_for_admin_with_authority() throws Exception {
        mockMvc.perform(get("/delete-user"))
            .andExpect(status().isOk());
    }

}
