package it.epicode.examu5w3final.repository;

import it.epicode.examu5w3final.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    public Optional<User> findByUsernameAndEmail(String username, String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
