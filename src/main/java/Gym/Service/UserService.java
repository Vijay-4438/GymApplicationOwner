package Gym.Service;

import Gym.Dto.ForgotPasswordRequest;
import Gym.Dto.LoginRequest;
import Gym.Dto.RegisterRequest;
import Gym.Dto.ResetPasswordRequest;
import Gym.Entity.Role;
import Gym.Entity.Users;
import Gym.Repository.UserReposiotory;
import Gym.exception.OTPExpiredException;
import Gym.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserReposiotory userReposiotory;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(LoginRequest request, Role role) {
        Users user = userReposiotory.findByEmailAndRole(request.getEmail(), role)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT including email and role
        return jwtUtil.generateToken(user.getEmail(), role.name());
    }


    public Users register(RegisterRequest request, Role role) {
        // Check if user already exists by email
        if (userReposiotory.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername()); // required for NOT NULL
        user.setPassword(request.getPassword()); // encode if needed
        user.setRole(role); // role is fixed by endpoint

        return userReposiotory.save(user);
    }
    public void verifyOTP(String email, String otp) {

        Users user = userReposiotory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check OTP
        if (user.getOtp() == null || !user.getOtp().equals(otp)) {
            throw new OTPExpiredException("Invalid OTP");
        }


        if (user.getOtpExpiry() == null ||
                user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new OTPExpiredException("OTP expired");
        }

        // Clear OTP after success
        user.setOtp(null);
        user.setOtpExpiry(null);

        userReposiotory.save(user);
    }

    public void forgotPassword(ForgotPasswordRequest request, String roleString) {
        Role role = Role.valueOf(roleString.toUpperCase());
        Users user = userReposiotory.findByEmailAndRole(request.getEmail(), role)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = generateOTP();
        user.setOtp(otp);
        userReposiotory.save(user);

        emailService.sendOtpEmail(user.getEmail(), otp);
    }


    public void resetPassword(ResetPasswordRequest request, String roleString) {
        Role role = Role.valueOf(roleString.toUpperCase());
        Users user = userReposiotory.findByEmailAndRole(request.getEmail(), role)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!request.getOtp().equals(user.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        user.setPassword(request.getNewPassword()); // encode ideally
        user.setOtp(null);
        userReposiotory.save(user);
    }

    public Users getUserByEmail(String email) {
        return userReposiotory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Users getUserById(Long id) {
        return userReposiotory.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public List<Users> getAllUsers() {
        return userReposiotory.findAll();
    }





    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}