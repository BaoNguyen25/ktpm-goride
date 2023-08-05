package com.example.goride.repositories;

import com.example.goride.models.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends MongoRepository<Booking,String> {
    List<Booking> findByUserId(String userId);
    List<Booking> findByDriverId(String driverId);
    Optional<Booking> findById(String bookingId);
}
