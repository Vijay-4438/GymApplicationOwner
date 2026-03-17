package Gym.Controller;

import Gym.Entity.Gymm;
import Gym.Service.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner")
public class OwnerGymController {

    @Autowired
    private GymService gymService;

    @PostMapping("/create")
    public Gymm create(@RequestBody Gymm gym) {
        return gymService.createGym(gym);
    }

    @GetMapping("/all")
    public List<Gymm> getAllGyms() {
        return gymService.getAll();
    }

    @GetMapping("/facilities")
    public List<String> getFacilites() {
        return gymService.getAllFacilities();
    }

    @PutMapping("/updateGym/{id}")
    public Gymm updatee(@PathVariable Long id, @RequestBody Gymm gym) {
        return gymService.updateGym(id, gym);
    }

    @PutMapping("/{id}/activate")
    public Gymm activateGym(@PathVariable Long id) {
        return gymService.activateGym(id);
    }

    @GetMapping("/active/{id}")
    public Gymm getActiveGymById(@PathVariable Long id) {
        return gymService.getActiveGymById(id);
    }
}
