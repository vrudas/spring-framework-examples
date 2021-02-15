package io.sfe.authorization;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessCheckController {

    @GetMapping("/admin/system-info")
    public String showSystemInfo() {
        return "systemInfo";
    }

    @GetMapping("/name")
    public String showUserName(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails.getUsername();
    }

    @GetMapping("/read-user")
    public UserDetails readUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

    @GetMapping("/delete-user")
    public UserDetails deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }



}
