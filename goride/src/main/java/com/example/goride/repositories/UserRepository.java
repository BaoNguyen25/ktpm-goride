package com.example.goride.repositories;

import com.example.goride.models.ERole;
import com.example.goride.models.Location;
import com.example.goride.models.Role;
import com.example.goride.models.User;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);


//    List<User> findByRolesNameAndLocationNear(ERole roles_name, Location location);
    List<User> findByRolesContains(Role role);

}
