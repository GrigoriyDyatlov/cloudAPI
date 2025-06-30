package ru.netology.cloudAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.cloudAPI.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
