package Gym.Controller;

import Gym.Dto.ForgotPasswordRequest;
import Gym.Dto.LoginRequest;
import Gym.Dto.ResetPasswordRequest;
import Gym.Entity.Users;
import Gym.Repository.UserReposiotory;
import Gym.Service.EmailService;
import Gym.Service.UserService;
import Gym.exception.OTPExpiredException;
import Gym.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserReposiotory userRepository;



    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private static final Logger LOGGER =
            Logger.getLogger(AuthController.class.getName());


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        try {

            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Username is already taken");
            }

            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Email is already registered");
            }

            Users savedUser = userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedUser);

        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {

        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            LOGGER.warning("Username or password is null");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username and password are required");
        }

        LOGGER.info("Attempting login with username: " + loginRequest.getEmail());

        Optional<Users> optionalUser =
                userRepository.findByUsername(loginRequest.getEmail());

        if (optionalUser.isPresent()) {

            Users existingUser = optionalUser.get();  // ✅ Extract user

            if (existingUser.getPassword()
                    .equals(loginRequest.getPassword())) {

                LOGGER.info("Password match for user: " + existingUser.getUsername());
                return ResponseEntity.ok(existingUser);

            } else {
                LOGGER.warning("Password mismatch for user: "
                        + existingUser.getUsername());
            }

        } else {
            LOGGER.warning("No user found with username: "
                    + loginRequest.getEmail());
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password");
    }


//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
//        try {
//            userService.sendForgotPasswordEmail(request.getEmail());
//            return ResponseEntity.ok("Password reset email sent successfully");
//        } catch (OTPExpiredException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
//        }
//    }

//    @PostMapping("/verify-otp")
//    public ResponseEntity<?> verifyOTP(@RequestParam String email, @RequestParam String otp) {
//        try {
//            userService.verifyOTP(email, otp); // Verify OTP
//            String token = jwtUtil.generateToken(email); // Generate JWT
//            return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
//        } catch (OTPExpiredException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTP verification failed: " + ex.getMessage());
//        } catch (Exception ex) {
//            ex.printStackTrace(); // <-- log the real error
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage()); // <-- show message
//        }
//    }
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestParam String email,
                                       @RequestParam String otp) {
        try {
            userService.verifyOTP(email, otp);

            // 🔥 Fetch user from DB
            Users user = userService.getUserByEmail(email);

            // Generate token with role
            String token = jwtUtil.generateToken(
                    user.getEmail(),
                    user.getRole().name()
            );

            return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");

        } catch (OTPExpiredException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("OTP verification failed: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }
    //    @GetMapping("/profile")
//    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
//        String token = authHeader.substring(7);
//        if (!jwtUtil.validateToken(token)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        String email = jwtUtil.extractEmail(token);
//        // Fetch user info by email
//        return ResponseEntity.ok("Welcome " + email);
//    }
    @GetMapping("/all")
    public List<Users> gett(){
        return userService.getAllUsers();
    }
//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
//        try {
//            userService.resetPassword(request);
//            return ResponseEntity.ok("Password reset successfully");
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
//        }
//
//    }

}
