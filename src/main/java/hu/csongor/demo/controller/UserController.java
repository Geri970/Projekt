package hu.csongor.demo.controller;

import hu.csongor.demo.dto.response.UserResponse;
import hu.csongor.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    public java.util.List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        return userService.getStats();
    }

    @PutMapping("/{id}/tiltas")
    public String tiltas(
            @PathVariable Integer id) {

        return userService.tiltas(id);
    }

    @PutMapping("/{id}/feloldas")
    public String feloldas(
            @PathVariable Integer id) {

        return userService.feloldas(id);
    }

    @PutMapping("/{id}/tanar")
    public String tanar(
            @PathVariable Integer id) {

        return userService.tanarraTesz(id);
    }

    @DeleteMapping("/{id}/delete")
    public String torles(
            @PathVariable Integer id) {

        return userService.torles(id);
    }
}