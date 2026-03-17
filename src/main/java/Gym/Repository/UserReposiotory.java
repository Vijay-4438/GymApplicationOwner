package Gym.Repository;

import Gym.Entity.Role;
import Gym.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReposiotory extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
   Optional<Users> findByEmailAndRole(String email, Role role);
}
