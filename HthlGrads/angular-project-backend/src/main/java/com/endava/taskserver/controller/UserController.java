package com.endava.taskserver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {
    @GetMapping("/auth/user/me")
    @PreAuthorize("isAuthenticated()")
    public Principal user(Principal principal) {
        return principal;
    }
}