package io.sfe.methodsecurity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessCheckController {

    @GetMapping("/admin/system-info")
    @PreAuthorize("hasRole('ADMIN')")
    public String showSystemInfo() {
        return "systemInfo";
    }

    @GetMapping("/name")
    @PreAuthorize("permitAll()")
    public String showUserName(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails.getUsername();
    }

    @GetMapping("/read-user")
    @PreAuthorize("hasAnyAuthority('READ_USERS')")
    public UserDetails readUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

    @GetMapping("/delete-user")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('DELETE_USERS')")
    public UserDetails deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

}
