package Gym.Controller;

import Gym.Entity.Gymm;
import Gym.Entity.Users;
import Gym.Service.GymService;
import Gym.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private GymService gymService;

    @Autowired
    private UserService userService;



    @GetMapping("/{id}")
    public Users getCustomerById(@PathVariable Long id) {

        return userService.getUserById(id);
    }

    @GetMapping("/gym/{id}")
    public Gymm getGymById(@PathVariable Long id) {

        return gymService.gettGymById(id);
    }


    }
