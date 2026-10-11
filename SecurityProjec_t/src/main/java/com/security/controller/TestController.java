package com.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public String profile() {
    return "Profile API working";
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String update() {
    return "Update API - ADMIN only";
    }


    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete() {
    return "Delete API - ADMIN only";
    }
}