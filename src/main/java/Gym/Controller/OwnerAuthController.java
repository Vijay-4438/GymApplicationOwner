package Gym.Controller;

import Gym.Dto.ForgotPasswordRequest;
import Gym.Dto.LoginRequest;
import Gym.Dto.OtpVerificationRequest;
import Gym.Dto.RegisterRequest;
import Gym.Dto.ResetPasswordRequest;
import Gym.Entity.Role;
import Gym.Entity.Users;
import Gym.Service.UserService;
import Gym.exception.OTPExpiredException;
import Gym.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner/auth")
public class OwnerAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Users register(@RequestBody RegisterRequest request) {

        return userService.register(request, Role.OWNER);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        return userService.login(request, Role.OWNER);
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody ForgotPasswordRequest request) {
        userService.forgotPassword(request, "OWNER");
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request, "OWNER");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody OtpVerificationRequest request) {
        try {
            userService.verifyOTP(request.getEmail(), request.getOtp());
            return ResponseEntity.ok("OTP verified successfully");
        } catch (OTPExpiredException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("OTP verification failed: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }
}
