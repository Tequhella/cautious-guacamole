package com.ynov.m2.advanced_software_development.cautious_guacamole.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
