package io.sfe.userflow.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void check_that_admin_user_exist_by_default() {
        boolean adminUserExist = userService.userExists("admin");

        assertThat(adminUserExist).isTrue();
    }

    @Test
    void user_was_created() {
        userService.createUser(
            User.builder()
                .username("user")
                .password("user")
                .roles("USER")
                .build()
        );

        boolean userExists = userService.userExists("user");

        assertThat(userExists).isTrue();
    }

    @Test
    void user_was_found() {
        userService.createUser(
            User.builder()
                .username("user_found")
                .password("user_found")
                .roles("USER")
                .build()
        );

        UserDetails user = userService.loadUserByUsername("user_found");

        assertThat(user).isNotNull();
        assertThat(user).extracting(UserDetails::getUsername).isEqualTo("user_found");
        assertThat(user).extracting(UserDetails::getPassword).isEqualTo("user_found");
    }

    @Test
    @WithMockUser(
        username = "user_with_password",
        password = "PASSWORD"
    )
    void user_password_was_changed() {
        userService.createUser(
            User.withUsername("user_with_password")
                .password("password")
                .roles("USER")
                .build()
        );

        userService.changePassword("PASSWORD", "password");

        UserDetails user = userService.loadUserByUsername("user_with_password");

        assertThat(user).extracting(UserDetails::getPassword).isEqualTo("password");
    }

    @Test
    void user_was_deleted() {
        userService.createUser(
            User.withUsername("user_to_delete")
                .password("")
                .roles("USER")
                .build()
        );

        assertThat(userService.userExists("user_to_delete")).isTrue();

        userService.deleteUser("user_to_delete");

        assertThat(userService.userExists("user_to_delete")).isFalse();
    }
}
