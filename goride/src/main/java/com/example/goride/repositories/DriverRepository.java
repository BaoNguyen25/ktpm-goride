package com.example.goride.repositories;

import com.example.goride.models.Driver;
import com.example.goride.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends MongoRepository<Driver, String> {
    Optional<Driver> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}