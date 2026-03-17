package Gym.Repository;

import Gym.Entity.Gymm;
import Gym.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymRepository  extends JpaRepository<Gymm,Long> {
    List<Gymm> findByActiveTrue();
    List<Gymm> findByOwner(Users owner);
}

