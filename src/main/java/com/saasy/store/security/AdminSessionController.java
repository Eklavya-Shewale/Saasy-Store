package com.saasy.store.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.security.Principal;

@RestController
@RequestMapping("/api/admin")
class AdminSessionController {

    @GetMapping("/session")
    Map<String, String> currentAdminSession(Principal administrator) {
        return Map.of("email", administrator.getName());
    }
}
