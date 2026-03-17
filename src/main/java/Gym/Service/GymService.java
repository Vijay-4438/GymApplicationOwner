package Gym.Service;

import Gym.Entity.Gymm;
import Gym.Entity.Role;
import Gym.Entity.Users;
import Gym.Repository.GymRepository;
import Gym.Repository.UserReposiotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GymService {
    @Autowired
    private UserReposiotory userReposiotory;

@Autowired
    private GymRepository gymRepository;
    public List<Gymm> getAll(){

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Users owner = userReposiotory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        return gymRepository.findByOwner(owner);
    }
    public Gymm getGymById(Long id){

        Gymm gym = gymRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gym not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Users owner = userReposiotory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (!gym.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You cannot access this gym");
        }

        return gym;
    }
    public Gymm createGym(Gymm gym) {
        gym.setActive(false);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("User not authenticated");
        }


        String email = authentication.getName().trim();


        Users owner = userReposiotory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found: " + email));


        if (owner.getRole() != Role.OWNER) {
            throw new RuntimeException("User is not an owner");
        }

        gym.setOwner(owner);

        System.out.println("Logged user email: " + email);
        System.out.println("User role from DB: " + owner.getRole());

        return gymRepository.save(gym);
    }
    public Gymm updateGym(Long id, Gymm updateGymm) {

        Gymm gym = gymRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gym id not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Users owner = userReposiotory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        // 🔥 CHECK OWNER
        if (!gym.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You cannot update this gym");
        }

        // update fields
        gym.setName(updateGymm.getName());
        gym.setAddress(updateGymm.getAddress());
        gym.setCity(updateGymm.getCity());
        gym.setArea(updateGymm.getArea());
        gym.setPin(updateGymm.getPin());
        gym.setLatitude(updateGymm.getLatitude());
        gym.setLongitude(updateGymm.getLongitude());
        gym.setHourlyRate(updateGymm.getHourlyRate());
        gym.setSlotCapacity(updateGymm.getSlotCapacity());
        gym.setActive(updateGymm.getActive());
        gym.setTimings(updateGymm.getTimings());
        gym.setFacilities(updateGymm.getFacilities());
        gym.setAboutPoints(updateGymm.getAboutPoints());
        gym.setGymPhotos(updateGymm.getGymPhotos());

        return gymRepository.save(gym);
    }
    public List<String> getAllFacilities() {

        List<Gymm> gyms = gymRepository.findAll();

        return gyms.stream()
                .filter(gym -> gym.getFacilities() != null)
                .flatMap(gym -> gym.getFacilities().stream())
                .distinct()


                .sorted()
                .toList();
    }
    public Gymm activateGym(Long id){
        Gymm gym = getGymById(id);

        // Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Users owner = userReposiotory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        // Check if the logged-in user is the owner
        if (!gym.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You are not authorized to activate this gym");
        }

        gym.setActive(true);
        return gymRepository.save(gym);
    }
    public Gymm getActiveGymById(Long id){
        Gymm gym = gymRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gym not found"));

        if (!gym.getActive()) {
            throw new RuntimeException("Gym not active");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Users owner = userReposiotory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (!gym.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You are not authorized to view this gym");
        }

        return gym;
    }
    public Gymm gettGymById(Long id) {

        Gymm gym = gymRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gym not found"));

        // Get logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Users user = userReposiotory.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        // Check role
        if (user.getRole() == Role.CUSTOMER) {
            // Customer: can only access active gyms
            if (!Boolean.TRUE.equals(gym.getActive())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Gym is not active");
            }
        } else {
            // Owner: can only access gyms they own
            if (!gym.getOwner().getId().equals(user.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot access this gym");
            }
        }

        return gym;
    }
    public List<Gymm> getAllActiveGyms() {
        return gymRepository.findByActiveTrue();
    }

    public Gymm activateGymForCustomer(Long id) {
        // Get gym
        Gymm gym = gymRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gym not found"));

        // Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Users customer = userReposiotory.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check role
        if (customer.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Only customers can activate this gym");
        }

        // Here you define your “activation” logic for customer
        // For example, you might mark this gym as “interested” or “reserved” for the customer
        gym.setActive(true);  // If your use case really wants this
        return gymRepository.save(gym);
    }
}
