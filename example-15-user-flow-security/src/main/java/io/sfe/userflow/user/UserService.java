package io.sfe.userflow.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(
        UserDetailsManager userDetailsManager,
        PasswordEncoder passwordEncoder
    ) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    void createUser(UserDetails user) {
        var encodedPassword = passwordEncoder.encode(user.getPassword());

        var userWithEncodedPassword = User.withUserDetails(user)
            .password(encodedPassword)
            .build();

        userDetailsManager.createUser(userWithEncodedPassword);
    }

    void deleteUser(String username) {
        userDetailsManager.deleteUser(username);
    }

    void changePassword(String oldPassword, String newPassword) {
        var encodedNewPassword = passwordEncoder.encode(newPassword);
        userDetailsManager.changePassword(oldPassword, encodedNewPassword);
    }

    boolean userExists(String username) {
        return userDetailsManager.userExists(username);
    }

    UserDetails loadUserByUsername(String username) {
        return userDetailsManager.loadUserByUsername(username);
    }

}
