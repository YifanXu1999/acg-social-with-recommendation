package com.acgsocial.conrtoller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping("/check/status")
    public String checkStatus() {
        return "User Service is up and running";
    }

    @RequestMapping("/role/developer")
    public String developerRole() {
        return "User has developer role";
    }
}
