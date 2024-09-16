package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Repository.AuthRepository;
import spring.boot.fainalproject.Service.AuthService;
import spring.boot.fainalproject.Service.CustomerService;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class authController {
    private final AuthService authService;

    @GetMapping("/getAllUsers")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(200).body(authService.findAll());
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity updateUser(@Valid @RequestBody User user, @PathVariable Integer id) {
        authService.updateUser(user, id);
        return ResponseEntity.status(200).body("User updated successfully");
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        authService.deleteUser(id);
        return ResponseEntity.status(200).body("User deleted successfully");
    }

    @DeleteMapping("/delete-by-admin/{userId}")
    public ResponseEntity deleteByAdmin(@AuthenticationPrincipal User user,@PathVariable Integer userId) {
        authService.deleteByAdmin(userId, userId);
        return ResponseEntity.status(200).body("User deleted successfully");
    }


}

