package io.sfe.jwt;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/me")
    public Map<String, Object> currentUser(@AuthenticationPrincipal Principal user) {
        return Map.of("name", user.getName());
    }
}
