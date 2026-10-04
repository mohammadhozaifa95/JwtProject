package com.security.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/profile")
    public String profile() {

        return "Profile API working";
    }


    @PutMapping("/update")
    public String update() {

        return "Update API - ADMIN only";
    }


    @DeleteMapping("/delete")
    public String delete() {

        return "Delete API - ADMIN only";
    }
}