package Gym.Controller;

import Gym.Dto.ForgotPasswordRequest;
import Gym.Dto.LoginRequest;
import Gym.Dto.RegisterRequest;
import Gym.Dto.ResetPasswordRequest;
import Gym.Entity.Role;
import Gym.Entity.Users;
import Gym.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/auth")
public class CustomerAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Users register(@RequestBody RegisterRequest request) {

        return userService.register(request, Role.CUSTOMER);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        return userService.login(request, Role.CUSTOMER);
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody ForgotPasswordRequest request) {
        userService.forgotPassword(request, "CUSTOMER");
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request, "CUSTOMER");
    }
}
