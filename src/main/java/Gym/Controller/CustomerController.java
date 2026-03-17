package Gym.Controller;

import Gym.Entity.Gymm;
import Gym.Service.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private GymService gymService;

    @GetMapping("/all")
    public List<Gymm> getAllActiveGyms() {
        return gymService.getAllActiveGyms();
    }

    @GetMapping("/{id}")
    public Gymm getGymById(@PathVariable Long id) {
        return gymService.gettGymById(id);
    }

    @PutMapping("/{id}/activate")
    public Gymm activateGym(@PathVariable Long id) {
        return gymService.activateGymForCustomer(id);
    }
}
