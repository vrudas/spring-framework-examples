package io.sfe.userflow.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsManager userDetailsManager;

    public UserService(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    void createUser(UserDetails user) {
        userDetailsManager.createUser(user);
    }

    void deleteUser(String username) {
        userDetailsManager.deleteUser(username);
    }

    void changePassword(String oldPassword, String newPassword) {
        userDetailsManager.changePassword(oldPassword, newPassword);
    }

    boolean userExists(String username) {
        return userDetailsManager.userExists(username);
    }

    UserDetails loadUserByUsername(String username) {
        return userDetailsManager.loadUserByUsername(username);
    }

}
