package com.acgsocial.conrtoller;


import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Secured("ROLE_developer")
    @DeleteMapping("/secured/{id}")
    public String deleteUser(@PathVariable Integer id) {
        return "User deleted successfully";
    }

    @PreAuthorize("hasRole('admin') or #id==#jwt.subject")
    @DeleteMapping("/preauthorize/{id}")
    public String preAuthorize(@PathVariable String  id, @AuthenticationPrincipal Jwt jwt) {

        return "User is preauthrozied successfully" + jwt.getSubject();
    }
}
