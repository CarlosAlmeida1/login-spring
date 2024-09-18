package com.example.login_spring.repositories;

import com.example.login_spring.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
