package dev.jorge.projects.gossipuerj.repository;

import dev.jorge.projects.gossipuerj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByVerificationCode(String verificationCode);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmailAndUsername(String email, String username);
}
